package zurihaqi.simple_gacha_sim.service.implementation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zurihaqi.simple_gacha_sim.dto.AuthDTO;
import zurihaqi.simple_gacha_sim.model.RefreshToken;
import zurihaqi.simple_gacha_sim.model.User;
import zurihaqi.simple_gacha_sim.repository.UserRepository;
import zurihaqi.simple_gacha_sim.security.JWTService;
import zurihaqi.simple_gacha_sim.service.AuthService;
import zurihaqi.simple_gacha_sim.service.RefreshTokenService;
import zurihaqi.simple_gacha_sim.utils.validation.AuthValidation;

import java.util.Date;

import static zurihaqi.simple_gacha_sim.utils.validation.AuthValidation.validateAuthDTO;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final RefreshTokenService refreshTokenService;

    @Override
    @Transactional
    public AuthDTO login(AuthDTO authDTO) {
        validateAuthDTO(authDTO, false);

        User user = userRepo.findByEmail(authDTO.getEmail()).orElseThrow(() -> new RuntimeException("Email not registered"));
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authDTO.getEmail(),
                        authDTO.getPassword()
                )
        );

        if (authentication.isAuthenticated()) {
            RefreshToken refreshToken = refreshTokenService.findByUser(user).orElseGet(() -> refreshTokenService.createRefreshToken(user));
            return AuthDTO.builder()
                    .accessToken(jwtService.generateToken(user))
                    .refreshToken(refreshToken.getToken())
                    .build();
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    @Override
    @Transactional
    public AuthDTO register(AuthDTO authDTO) {
        validateAuthDTO(authDTO, true);

        if (userRepo.findByEmail(authDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        if (userRepo.findByUsername(authDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Username already taken");
        }

        User user = User.builder()
                .username(authDTO.getUsername())
                .email(authDTO.getEmail())
                .role(User.Role.USER)
                .password(passwordEncoder.encode(authDTO.getPassword()))
                .createdAt(new Date())
                .updatedAt(null)
                .build();

        user = userRepo.save(user);
        return AuthDTO.builder()
                .username(user.getActualUsername())
                .email(user.getEmail())
                .accessToken(jwtService.generateToken(user))
                .refreshToken(refreshTokenService.createRefreshToken(user).getToken())
                .build();
    }
}

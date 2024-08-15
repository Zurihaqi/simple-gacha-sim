package zurihaqi.simple_gacha_sim.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import zurihaqi.simple_gacha_sim.dto.AuthDTO;
import zurihaqi.simple_gacha_sim.model.RefreshToken;
import zurihaqi.simple_gacha_sim.model.User;
import zurihaqi.simple_gacha_sim.repository.RefreshTokenRepository;
import zurihaqi.simple_gacha_sim.security.JWTService;
import zurihaqi.simple_gacha_sim.service.RefreshTokenService;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JWTService jwtService;


    @Override
    public RefreshToken createRefreshToken(User user) {
        return refreshTokenRepository.save(
                RefreshToken.builder()
                        .user(user)
                        .token(UUID.randomUUID().toString())
                        .expiryDate(Instant.now().plusSeconds(86400))
                        .createdAt(new Date())
                        .updatedAt(null)
                        .build()
        );
    }

    @Override
    public Optional<RefreshToken> findByToken(String refreshToken) {
        return refreshTokenRepository.findByToken(refreshToken);
    }

    @Override
    public Optional<RefreshToken> findByUser(User user) {
        return refreshTokenRepository.findByUser(user);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        Optional<RefreshToken> refreshTokenExist = findByToken(refreshToken.getToken());
        refreshTokenExist.ifPresent(refreshToken1 -> {
            if (refreshToken1.getExpiryDate().compareTo(Instant.now()) < 0) {
                refreshTokenRepository.deleteById(refreshToken1.getId());
                throw new RuntimeException("Refresh token was expired");
            }
        });
        return refreshTokenExist.orElseThrow(() -> new RuntimeException("Refresh token not found"));
    }

    @Override
    public AuthDTO refreshToken(String refreshToken) {
        String accessToken = refreshTokenRepository.findByToken(refreshToken)
                .map(this::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(jwtService::generateToken)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        return AuthDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}

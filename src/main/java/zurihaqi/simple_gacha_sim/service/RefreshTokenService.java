package zurihaqi.simple_gacha_sim.service;

import zurihaqi.simple_gacha_sim.dto.AuthDTO;
import zurihaqi.simple_gacha_sim.model.RefreshToken;
import zurihaqi.simple_gacha_sim.model.User;

import java.util.Optional;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(User user);

    Optional<RefreshToken> findByToken(String refreshToken);

    Optional<RefreshToken> findByUser(User user);

    RefreshToken verifyExpiration(RefreshToken refreshToken);

    AuthDTO refreshToken(String refreshToken);
}

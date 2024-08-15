package zurihaqi.simple_gacha_sim.repository;

import zurihaqi.simple_gacha_sim.model.RefreshToken;
import zurihaqi.simple_gacha_sim.model.User;

import java.util.Optional;

public interface RefreshTokenRepository {
    Optional<RefreshToken> findById(Long id);

    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUser(User user);

    RefreshToken save(RefreshToken refreshToken);

    void deleteById(Long id);

    void deleteByUserId(Long userId);

    long count();
}

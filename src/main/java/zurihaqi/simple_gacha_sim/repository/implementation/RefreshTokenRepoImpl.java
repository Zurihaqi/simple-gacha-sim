package zurihaqi.simple_gacha_sim.repository.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import zurihaqi.simple_gacha_sim.model.RefreshToken;
import zurihaqi.simple_gacha_sim.model.User;
import zurihaqi.simple_gacha_sim.repository.RefreshTokenRepository;

import java.util.Optional;

@Repository
public class RefreshTokenRepoImpl implements RefreshTokenRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<RefreshToken> findById(Long id) {
        String sql = "SELECT * FROM refresh_tokens WHERE id = :id";
        Query query = entityManager.createNativeQuery(sql, RefreshToken.class);
        query.setParameter("id", id);
        try{
            return Optional.ofNullable((RefreshToken) query.getSingleResult());
        } catch (EmptyResultDataAccessException | NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        String sql = "SELECT * FROM refresh_tokens WHERE token = :token";
        Query query = entityManager.createNativeQuery(sql, RefreshToken.class);
        query.setParameter("token", token);

        try {
            return Optional.ofNullable((RefreshToken) query.getSingleResult());
        } catch (EmptyResultDataAccessException | NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<RefreshToken> findByUser(User user) {
        String sql = "SELECT * FROM refresh_tokens WHERE user_id = :userId";
        Query query = entityManager.createNativeQuery(sql, RefreshToken.class);
        query.setParameter("userId", user.getId());
        try{
            return Optional.ofNullable((RefreshToken) query.getSingleResult());
        } catch (EmptyResultDataAccessException | NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        if (refreshToken.getId() == null) {
            entityManager.persist(refreshToken);
        } else {
            refreshToken = entityManager.merge(refreshToken);
        }
        return refreshToken;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM refresh_tokens WHERE id = :id";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void deleteByUserId(Long userId) {
        String sql = "DELETE FROM refresh_tokens WHERE user_id = :userId";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("userId", userId);
        query.executeUpdate();
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM refresh_tokens";
        Query query = entityManager.createNativeQuery(sql);
        return ((Number) query.getSingleResult()).longValue();
    }
}

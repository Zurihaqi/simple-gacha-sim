package zurihaqi.simple_gacha_sim.repository.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import zurihaqi.simple_gacha_sim.model.User;
import zurihaqi.simple_gacha_sim.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepoImpl implements UserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        Query query = entityManager.createNativeQuery(sql, User.class);
        return query.getResultList();
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        String sql = "SELECT * FROM users";
        Query query = entityManager.createNativeQuery(sql, User.class);

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<User> users = query.getResultList();

        String countSql = "SELECT COUNT(*) FROM users";
        Query countQuery = entityManager.createNativeQuery(countSql);
        long total = ((Number) countQuery.getSingleResult()).longValue();

        return new PageImpl<>(users, pageable, total);
    }

    @Override
    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = :id";
        Query query = entityManager.createNativeQuery(sql, User.class);
        query.setParameter("id", id);

        try {
            return Optional.ofNullable((User) query.getSingleResult());
        } catch (EmptyResultDataAccessException | NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = :username";
        Query query = entityManager.createNativeQuery(sql, User.class);
        query.setParameter("username", username);

        try {
            return Optional.ofNullable((User) query.getSingleResult());
        } catch (EmptyResultDataAccessException | NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = :email";
        Query query = entityManager.createNativeQuery(sql, User.class);
        query.setParameter("email", email);

        try {
            return Optional.ofNullable((User) query.getSingleResult());
        } catch (EmptyResultDataAccessException | NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            entityManager.persist(user);
        } else {
            user = entityManager.merge(user);
        }
        return user;
    }

    @Override
    public void deleteById(Long id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM users";
        Query query = entityManager.createNativeQuery(sql);
        return ((Number) query.getSingleResult()).longValue();
    }
}

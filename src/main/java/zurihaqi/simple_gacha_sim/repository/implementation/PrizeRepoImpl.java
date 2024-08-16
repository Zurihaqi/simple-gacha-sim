package zurihaqi.simple_gacha_sim.repository.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import zurihaqi.simple_gacha_sim.model.Prize;
import zurihaqi.simple_gacha_sim.model.Tier;
import zurihaqi.simple_gacha_sim.repository.PrizeRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class PrizeRepoImpl implements PrizeRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Prize> findAll() {
        String sql = "SELECT * FROM prizes";
        Query query = entityManager.createNativeQuery(sql, Prize.class);
        return query.getResultList();
    }

    @Override
    public Page<Prize> findAll(Pageable pageable) {
        String sql = "SELECT * FROM prizes";
        Query query = entityManager.createNativeQuery(sql, Prize.class);

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Prize> prizes = query.getResultList();

        String countSql = "SELECT COUNT(*) FROM prizes";
        Query countQuery = entityManager.createNativeQuery(countSql);
        long total = ((Number) countQuery.getSingleResult()).longValue();

        return new PageImpl<>(prizes, pageable, total);
    }

    @Override
    public Optional<Prize> findById(Long id) {
        String sql = "SELECT * FROM prizes WHERE id = :id";
        Query query = entityManager.createNativeQuery(sql, Prize.class);
        query.setParameter("id", id);

        try {
            return Optional.ofNullable((Prize) query.getSingleResult());
        } catch (EmptyResultDataAccessException | NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Prize> findByTierId(Long tierId) {
        String sql = "SELECT * FROM prizes WHERE tier_id = :tierId";
        Query query = entityManager.createNativeQuery(sql, Prize.class);
        query.setParameter("tierId", tierId);
        return query.getResultList();
    }

    @Override
    public Prize save(Prize prize) {
        if (prize.getId() == null) {
            entityManager.persist(prize);
        } else {
            prize = entityManager.merge(prize);
        }
        return prize;
    }

    @Override
    public List<Prize> saveAll(List<Prize> prizes) {
        for (Prize prize : prizes) {
            save(prize);
        }
        return prizes;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM prizes WHERE id = :id";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM prizes";
        Query query = entityManager.createNativeQuery(sql);
        return ((Number) query.getSingleResult()).longValue();
    }

    @Override
    public Set<Prize> findAllById(List<Object> list) {
        String sql = "SELECT * FROM prizes WHERE id IN (:ids)";
        Query query = entityManager.createNativeQuery(sql, Prize.class);
        query.setParameter("ids", list);
        return (Set<Prize>) query.getResultList();
    }
}

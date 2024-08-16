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
import zurihaqi.simple_gacha_sim.model.Tier;
import zurihaqi.simple_gacha_sim.model.User;
import zurihaqi.simple_gacha_sim.repository.TierRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class TierRepoImpl implements TierRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Tier> findAll() {
        String sql = "SELECT * FROM tiers";
        Query query = entityManager.createNativeQuery(sql, Tier.class);
        return query.getResultList();
    }

    @Override
    public Page<Tier> findAll(Pageable pageable, String name) {
        StringBuilder sql = new StringBuilder("SELECT * FROM tiers");

        if (name != null && !name.isEmpty()) {
            sql.append(" WHERE name LIKE :name");
        }

        Query query = entityManager.createNativeQuery(sql.toString(), Tier.class);

        if (name != null && !name.isEmpty()) {
            query.setParameter("name", "%" + name + "%");
        }

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Tier> tiers = query.getResultList();

        String countSql = "SELECT COUNT(*) FROM tiers";
        if (name != null && !name.isEmpty()) {
            countSql += " WHERE name LIKE :name";
        }
        Query countQuery = entityManager.createNativeQuery(countSql);
        if (name != null && !name.isEmpty()) {
            countQuery.setParameter("name", "%" + name + "%");
        }
        long total = ((Number) countQuery.getSingleResult()).longValue();

        return new PageImpl<>(tiers, pageable, total);
    }

    @Override
    public Optional<Tier> findById(Long id) {
        String sql = "SELECT * FROM tiers WHERE id = :id";
        Query query = entityManager.createNativeQuery(sql, Tier.class);
        query.setParameter("id", id);
        try {
            return Optional.ofNullable((Tier) query.getSingleResult());
        } catch (EmptyResultDataAccessException | NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Tier> findByName(String name) {
        String sql = "SELECT * FROM tiers WHERE name = :name";
        Query query = entityManager.createNativeQuery(sql, Tier.class);
        query.setParameter("name", name);

        try{
            return Optional.ofNullable((Tier) query.getSingleResult());
        } catch (EmptyResultDataAccessException | NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Tier save(Tier tier) {
        if (tier.getId() == null) {
            entityManager.persist(tier);
        } else {
            tier = entityManager.merge(tier);
        }
        return tier;
    }

    @Override
    public List<Tier> saveAll(List<Tier> tiers) {
        for (Tier tier : tiers) {
            if (tier.getId() == null) {
                entityManager.persist(tier);
            } else {
                entityManager.merge(tier);
            }
        }
        return tiers;
    }

    @Override
    public void deleteById(Long id) {
        Tier tier = entityManager.find(Tier.class, id);
        if (tier != null) {
            entityManager.remove(tier);
        }
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM tiers";
        Query query = entityManager.createNativeQuery(sql);
        return ((Number) query.getSingleResult()).longValue();
    }
}

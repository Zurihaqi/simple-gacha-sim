package zurihaqi.simple_gacha_sim.repository.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import zurihaqi.simple_gacha_sim.model.Inventory;
import zurihaqi.simple_gacha_sim.repository.InventoryRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class InventoryRepoImpl implements InventoryRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Inventory> findAll() {
        String sql = "SELECT * FROM inventories";
        Query query = entityManager.createNativeQuery(sql, Inventory.class);
        return query.getResultList();
    }

    @Override
    public Page<Inventory> findAll(Pageable pageable) {
        String sql = "SELECT * FROM inventories";
        Query query = entityManager.createNativeQuery(sql, Inventory.class);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Inventory> inventories = query.getResultList();
        long total = (long) entityManager.createNativeQuery("SELECT COUNT(*) FROM inventories").getSingleResult();
        return new PageImpl<>(inventories, pageable, total);
    }

    @Override
    public Optional<Inventory> findById(Long id) {
        String sql = "SELECT * FROM inventories WHERE id = :id";
        Query query = entityManager.createNativeQuery(sql, Inventory.class);
        query.setParameter("id", id);
        try {
            return Optional.ofNullable((Inventory) query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Inventory> findByUserId(Long userId) {
        String sql = "SELECT * FROM inventories WHERE user_id = :userId";
        Query query = entityManager.createNativeQuery(sql, Inventory.class);
        query.setParameter("userId", userId);
        List<Inventory> inventories = query.getResultList();

        if (inventories.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(inventories.get(0));
        }
    }

    @Override
    public Inventory save(Inventory inventory) {
        if (inventory.getId() == null) {
            String sql = "INSERT INTO inventories (created_at, updated_at, user_id) VALUES (:createdAt, :updatedAt, :userId)";
            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("createdAt", inventory.getCreatedAt());
            query.setParameter("updatedAt", inventory.getUpdatedAt());
            query.setParameter("userId", inventory.getUser().getId());
            query.executeUpdate();
        } else {
            String sql = "UPDATE inventories SET updated_at = :updatedAt, user_id = :userId WHERE id = :id";
            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("updatedAt", inventory.getUpdatedAt());
            query.setParameter("userId", inventory.getUser().getId());
            query.setParameter("id", inventory.getId());
            query.executeUpdate();
        }
        return inventory;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM inventories WHERE id = :id";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM inventories";
        Query query = entityManager.createNativeQuery(sql);
        return ((Number) query.getSingleResult()).longValue();
    }
}

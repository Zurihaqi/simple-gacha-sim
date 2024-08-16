package zurihaqi.simple_gacha_sim.repository.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import zurihaqi.simple_gacha_sim.model.InventoryPrize;
import zurihaqi.simple_gacha_sim.repository.InventoryPrizeRepository;

@Repository
public class InventoryPrizeRepoImpl implements InventoryPrizeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(InventoryPrize inventoryPrize) {
        if (inventoryPrize.getId() == null) {
            entityManager.persist(inventoryPrize);
        } else {
            entityManager.merge(inventoryPrize);
        }
    }
}

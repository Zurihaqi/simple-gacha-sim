package zurihaqi.simple_gacha_sim.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zurihaqi.simple_gacha_sim.model.Inventory;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository {
    List<Inventory> findAll();

    Page<Inventory> findAll(Pageable pageable);

    Optional<Inventory> findById(Long id);

    Optional<Inventory> findByUserId(Long userId);

    Inventory save(Inventory inventory);

    void deleteById(Long id);

    long count();
}

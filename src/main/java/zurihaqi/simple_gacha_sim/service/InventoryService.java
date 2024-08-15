package zurihaqi.simple_gacha_sim.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zurihaqi.simple_gacha_sim.dto.InventoryDTO;

public interface InventoryService {
    Page<InventoryDTO> getAllInventories(Pageable pageable);

    InventoryDTO createInventory(InventoryDTO inventoryDTO);

    InventoryDTO updateInventory(Long id, InventoryDTO inventoryDTO);

    InventoryDTO getOneInventory(Long id);

    void deleteInventoryById(Long id);
}

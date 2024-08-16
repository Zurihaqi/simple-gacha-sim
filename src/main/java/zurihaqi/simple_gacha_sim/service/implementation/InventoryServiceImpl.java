package zurihaqi.simple_gacha_sim.service.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import zurihaqi.simple_gacha_sim.dto.InventoryDTO;
import zurihaqi.simple_gacha_sim.model.Inventory;
import zurihaqi.simple_gacha_sim.model.Prize;
import zurihaqi.simple_gacha_sim.model.User;
import zurihaqi.simple_gacha_sim.repository.InventoryRepository;
import zurihaqi.simple_gacha_sim.repository.PrizeRepository;
import zurihaqi.simple_gacha_sim.repository.UserRepository;
import zurihaqi.simple_gacha_sim.service.InventoryService;
import zurihaqi.simple_gacha_sim.utils.ObjectMapper;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;
    private final UserRepository userRepository;
    private final PrizeRepository prizeRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<InventoryDTO> getAllInventories(Pageable pageable) {
        List<InventoryDTO> mappedInventory = inventoryRepository.findAll(pageable).stream().map(ObjectMapper::toInventoryDTO).toList();
        return new PageImpl<>(mappedInventory, pageable, inventoryRepository.count());
    }

    @Override
    @Transactional
    public InventoryDTO createInventory(InventoryDTO inventoryDTO) {
        Inventory newInventory = new Inventory();
        newInventory.setCreatedAt(new Date());
        newInventory.setUpdatedAt(null);
        newInventory.setUser(userRepository.findById(inventoryDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found")));

        newInventory = inventoryRepository.save(newInventory);

        Inventory finalNewInventory = newInventory;
        inventoryDTO.getPrizes().forEach(prizeDTO -> {
            Prize prize = prizeRepository.findById(prizeDTO.getId()).orElseThrow(() -> new RuntimeException("Prize not found"));
            String sql = "INSERT INTO inventory_prizes (inventory_id, prize_id, amount) VALUES (:inventoryId, :prizeId, :amount)";
            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("inventoryId", finalNewInventory.getId());
            query.setParameter("prizeId", prize.getId());
            query.setParameter("amount", prizeDTO.getAmount());
            query.executeUpdate();
        });

        return ObjectMapper.toInventoryDTO(newInventory);
    }

    @Override
    @Transactional
    public InventoryDTO updateInventory(Long id, InventoryDTO inventoryDTO) {
        Inventory existingInventory = inventoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Inventory not found"));
        if (inventoryDTO.getPrizes() != null) {
            String deleteSql = "DELETE FROM inventory_prizes WHERE inventory_id = :inventoryId";
            Query deleteQuery = entityManager.createNativeQuery(deleteSql);
            deleteQuery.setParameter("inventoryId", existingInventory.getId());
            deleteQuery.executeUpdate();

            inventoryDTO.getPrizes().forEach(prizeDTO -> {
                Prize prize = prizeRepository.findById(prizeDTO.getId()).orElseThrow(() -> new RuntimeException("Prize not found"));
                String insertSql = "INSERT INTO inventory_prizes (inventory_id, prize_id, amount) VALUES (:inventoryId, :prizeId, :amount)";
                Query insertQuery = entityManager.createNativeQuery(insertSql);
                insertQuery.setParameter("inventoryId", existingInventory.getId());
                insertQuery.setParameter("prizeId", prize.getId());
                insertQuery.setParameter("amount", prizeDTO.getAmount());
                insertQuery.executeUpdate();
            });
        }
        if (inventoryDTO.getUserId() != null) {
            existingInventory.setUser(userRepository.findById(inventoryDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found")));
        }
        return ObjectMapper.toInventoryDTO(inventoryRepository.save(existingInventory));
    }

    @Override
    public InventoryDTO getOneInventory(Long id) {
        return ObjectMapper.toInventoryDTO(inventoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Inventory not found")));
    }

    @Override
    public InventoryDTO getOwnInventory() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ObjectMapper.toInventoryDTO(inventoryRepository.findByUserId(user.getId()).orElseThrow(() -> new RuntimeException("Inventory not found")));
    }

    @Override
    @Transactional
    public void deleteInventoryById(Long id) {
        inventoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Inventory not found"));
        inventoryRepository.deleteById(id);
    }
}
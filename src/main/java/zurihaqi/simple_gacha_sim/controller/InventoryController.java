package zurihaqi.simple_gacha_sim.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zurihaqi.simple_gacha_sim.dto.InventoryDTO;
import zurihaqi.simple_gacha_sim.service.InventoryService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/inventories")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @RequestMapping
    public ResponseEntity<?> getAllInventories(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(inventoryService.getAllInventories(pageable));
    }

    @RequestMapping("/{id}")
    public ResponseEntity<?> getOneInventory(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryService.getOneInventory(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateInventory(@PathVariable Long id, @RequestBody InventoryDTO inventoryDTO) {
        return ResponseEntity.ok(inventoryService.updateInventory(id, inventoryDTO));
    }

    @PostMapping
    public ResponseEntity<?> createInventory(@RequestBody InventoryDTO inventoryDTO) {
        return ResponseEntity.ok(inventoryService.createInventory(inventoryDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventoryById(id);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "Success");
        response.put("message", "Inventory deleted");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

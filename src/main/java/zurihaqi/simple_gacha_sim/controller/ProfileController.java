package zurihaqi.simple_gacha_sim.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zurihaqi.simple_gacha_sim.service.InventoryService;
import zurihaqi.simple_gacha_sim.service.UserService;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final UserService userService;
    private final InventoryService inventoryService;

    @RequestMapping
    public ResponseEntity<?> getOwnProfile() {
        return ResponseEntity.ok(userService.getOwnProfile());
    }

    @RequestMapping("/inventory")
    public ResponseEntity<?> getOwnInventory() {
        return ResponseEntity.ok(inventoryService.getOwnInventory());
    }
}

package zurihaqi.simple_gacha_sim.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zurihaqi.simple_gacha_sim.model.Tier;
import zurihaqi.simple_gacha_sim.service.TierService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/tiers")
@RequiredArgsConstructor
public class TierController {
    private final TierService tierService;

    @PostMapping
    public ResponseEntity<?> createTier(@RequestBody Tier tier) {
        return ResponseEntity.ok(tierService.createTier(tier));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTier(@PathVariable Long id, @RequestBody Tier tier) {
        return ResponseEntity.ok(tierService.updateTier(id, tier));
    }

    @RequestMapping
    public ResponseEntity<?> getAllTiers(@PageableDefault Pageable pageable, @RequestParam(required = false) String name) {
        return ResponseEntity.ok(tierService.getAllTiers(pageable, name));
    }

    @RequestMapping("/{id}")
    public ResponseEntity<?> getOneTier(@PathVariable Long id) {
        return ResponseEntity.ok(tierService.getOneTier(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTier(@PathVariable Long id) {
        tierService.deleteTier(id);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "Success");
        response.put("message", "Tier deleted");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

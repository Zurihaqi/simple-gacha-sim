package zurihaqi.simple_gacha_sim.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zurihaqi.simple_gacha_sim.dto.PrizeDTO;
import zurihaqi.simple_gacha_sim.service.PrizeService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/prizes")
@RequiredArgsConstructor
public class PrizeController {
    private final PrizeService prizeService;

    @RequestMapping
    public ResponseEntity<?> getAllPrizes(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(prizeService.getAllPrizes(pageable));
    }

    @RequestMapping("/{id}")
    public ResponseEntity<?> getOnePrize(@PathVariable Long id) {
        return ResponseEntity.ok(prizeService.getOnePrize(id));
    }

    @PostMapping
    public ResponseEntity<?> createPrize(@RequestBody PrizeDTO prizeDto) {
        return ResponseEntity.ok(prizeService.createPrize(prizeDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePrize(@PathVariable Long id, @RequestBody PrizeDTO prizeDto) {
        return ResponseEntity.ok(prizeService.updatePrize(id, prizeDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePrize(@PathVariable Long id) {
        prizeService.deletePrize(id);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "Success");
        response.put("message", "Prize deleted");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

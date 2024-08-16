package zurihaqi.simple_gacha_sim.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zurihaqi.simple_gacha_sim.dto.PrizeDTO;
import zurihaqi.simple_gacha_sim.service.GachaService;

@RestController
@RequestMapping("/api/v1/gacha")
@RequiredArgsConstructor
public class GachaController {
    private final GachaService gachaService;

    @PostMapping
    public ResponseEntity<PrizeDTO> pullOnePrize() {
        return ResponseEntity.ok(gachaService.pullOnePrize());
    }

    @PostMapping("/{count}")
    public ResponseEntity<Page<PrizeDTO>> pullManyPrizes(Pageable pageable, @PathVariable Integer count) {
        return ResponseEntity.ok(gachaService.pullManyPrizes(pageable, count));
    }
}

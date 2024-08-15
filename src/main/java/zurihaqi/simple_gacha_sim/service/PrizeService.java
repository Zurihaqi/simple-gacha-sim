package zurihaqi.simple_gacha_sim.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zurihaqi.simple_gacha_sim.dto.PrizeDTO;

public interface PrizeService {
    Page<PrizeDTO> getAllPrizes(Pageable pageable);

    PrizeDTO getOnePrize(Long id);

    PrizeDTO createPrize(PrizeDTO prizeDto);

    PrizeDTO updatePrize(Long id, PrizeDTO prizeDto);

    void deletePrize(Long id);
}

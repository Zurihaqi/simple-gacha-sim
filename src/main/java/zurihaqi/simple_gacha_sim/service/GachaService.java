package zurihaqi.simple_gacha_sim.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zurihaqi.simple_gacha_sim.dto.PrizeDTO;

public interface GachaService {
    PrizeDTO pullOnePrize();
    Page<PrizeDTO> pullManyPrizes(Pageable pageable, Integer count);
}

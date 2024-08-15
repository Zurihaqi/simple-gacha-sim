package zurihaqi.simple_gacha_sim.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zurihaqi.simple_gacha_sim.model.Tier;

import java.util.Optional;

public interface TierService {

    Page<Tier> getAllTiers(Pageable pageable, String name);

    Tier getOneTier(Long id);


    Tier createTier(Tier tier);

    Tier updateTier(Long id, Tier tier);

    void deleteTier(Long id);
}

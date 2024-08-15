package zurihaqi.simple_gacha_sim.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zurihaqi.simple_gacha_sim.model.Tier;

import java.util.List;
import java.util.Optional;

public interface TierRepository {
    List<Tier> findAll();

    Page<Tier> findAll(Pageable pageable, String name);

    Optional<Tier> findById(Long id);

    Optional<Tier> findByName(String name);

    Tier save(Tier tier);

    void deleteById(Long id);

    long count();
}

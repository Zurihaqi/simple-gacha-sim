package zurihaqi.simple_gacha_sim.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zurihaqi.simple_gacha_sim.model.Prize;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PrizeRepository {
    List<Prize> findAll();

    Page<Prize> findAll(Pageable pageable);

    Optional<Prize> findById(Long id);

    List<Prize> findByTierId(Long tierId);

    Prize save(Prize prize);

    void deleteById(Long id);

    long count();

    Set<Prize> findAllById(List<Object> list);
}

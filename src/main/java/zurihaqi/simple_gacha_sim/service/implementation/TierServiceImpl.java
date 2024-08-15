package zurihaqi.simple_gacha_sim.service.implementation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zurihaqi.simple_gacha_sim.model.Tier;
import zurihaqi.simple_gacha_sim.repository.TierRepository;
import zurihaqi.simple_gacha_sim.service.TierService;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TierServiceImpl implements TierService {
    private final TierRepository tierRepository;

    @Override
    public Page<Tier> getAllTiers(Pageable pageable, String name) {
        return tierRepository.findAll(pageable, name);
    }

    @Override
    public Tier getOneTier(Long id) {
        return tierRepository.findById(id).orElseThrow(() -> new RuntimeException("Tier not found"));
    }

    @Override
    @Transactional
    public Tier createTier(Tier tier) {
        if(tier.getName() == null) {
            throw new RuntimeException("Tier name cannot be null");
        }
        if(tier.getDropRate() == null) {
            throw new RuntimeException("Tier drop rate cannot be null");
        }
        if(tierRepository.findByName(tier.getName()).isPresent()) {
            throw new RuntimeException("Tier already exists");
        }

        return tierRepository.save(
                Tier.builder()
                .name(tier.getName())
                .dropRate(tier.getDropRate())
                .createdAt(new Date())
                .updatedAt(null)
                .build()
        );
    }

    @Override
    @Transactional
    public Tier updateTier(Long id, Tier tier) {
        Tier newTier = tierRepository.findById(id).orElseThrow(() -> new RuntimeException("Tier not found"));
        if(tier.getName() != null) newTier.setName(tier.getName());
        if(tier.getDropRate() != null) newTier.setDropRate(tier.getDropRate());
        newTier.setUpdatedAt(new Date());
        return tierRepository.save(newTier);
    }

    @Override
    @Transactional
    public void deleteTier(Long id) {
        tierRepository.findById(id).orElseThrow(() -> new RuntimeException("Tier not found"));
        tierRepository.deleteById(id);
    }
}

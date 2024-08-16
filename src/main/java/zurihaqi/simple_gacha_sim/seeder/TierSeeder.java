package zurihaqi.simple_gacha_sim.seeder;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import zurihaqi.simple_gacha_sim.model.Tier;
import zurihaqi.simple_gacha_sim.repository.TierRepository;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
@Transactional
public class TierSeeder implements CommandLineRunner {
    private final TierRepository tierRepository;

    @Override
    public void run(String... args) throws Exception {
        seedTiers();
    }

    public void seedTiers() {
        if (tierRepository.count() > 0) {
            return;
        }

        tierRepository.saveAll(
                List.of(
                        Tier.builder()
                                .name("Common")
                                .dropRate(0.8)
                                .createdAt(new Date())
                                .updatedAt(null)
                                .build(),
                        Tier.builder()
                                .name("Uncommon")
                                .dropRate(0.5)
                                .createdAt(new Date())
                                .updatedAt(null)
                                .build(),
                        Tier.builder()
                                .name("Rare")
                                .dropRate(0.3)
                                .createdAt(new Date())
                                .updatedAt(null)
                                .build(),
                        Tier.builder()
                                .name("Epic")
                                .dropRate(0.2)
                                .createdAt(new Date())
                                .updatedAt(null)
                                .build(),
                        Tier.builder()
                                .name("Legendary")
                                .dropRate(0.1)
                                .createdAt(new Date())
                                .updatedAt(null)
                                .build()
                )
        );
    }
}


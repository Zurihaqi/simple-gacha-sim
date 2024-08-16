package zurihaqi.simple_gacha_sim.seeder;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import zurihaqi.simple_gacha_sim.model.Prize;
import zurihaqi.simple_gacha_sim.model.Tier;
import zurihaqi.simple_gacha_sim.repository.PrizeRepository;
import zurihaqi.simple_gacha_sim.repository.TierRepository;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
@Transactional
public class PrizeSeeder implements CommandLineRunner {
    private final PrizeRepository prizeRepository;
    private final TierRepository tierRepository;
    private final TierSeeder tierSeeder;

    @Override
    public void run(String... args) throws Exception {
        tierSeeder.seedTiers();
        seedPrizes();
    }

    public void seedPrizes() {
        if (prizeRepository.count() == 0) {
            Tier common = tierRepository.findByName("Common").orElseThrow(() -> new RuntimeException("Common tier not found"));
            Tier uncommon = tierRepository.findByName("Uncommon").orElseThrow(() -> new RuntimeException("Uncommon tier not found"));
            Tier rare = tierRepository.findByName("Rare").orElseThrow(() -> new RuntimeException("Rare tier not found"));
            Tier epic = tierRepository.findByName("Epic").orElseThrow(() -> new RuntimeException("Epic tier not found"));
            Tier legendary = tierRepository.findByName("Legendary").orElseThrow(() -> new RuntimeException("Legendary tier not found"));

            prizeRepository.saveAll(
                    List.of(
                            Prize.builder()
                                    .name("Common Sword")
                                    .description("A basic sword used by new adventurers.")
                                    .imageUrl("/images/prizes/common_sword.png")
                                    .tier(common)
                                    .createdAt(new Date())
                                    .build(),
                            Prize.builder()
                                    .name("Uncommon Shield")
                                    .description("A shield with some magical properties.")
                                    .imageUrl("/images/prizes/uncommon_shield.png")
                                    .tier(uncommon)
                                    .createdAt(new Date())
                                    .build(),
                            Prize.builder()
                                    .name("Rare Armor")
                                    .description("Armor that provides excellent protection.")
                                    .imageUrl("/images/prizes/rare_armor.png")
                                    .tier(rare)
                                    .createdAt(new Date())
                                    .build(),
                            Prize.builder()
                                    .name("Epic Bow")
                                    .description("A bow that never misses its target.")
                                    .imageUrl("/images/prizes/epic_bow.png")
                                    .tier(epic)
                                    .createdAt(new Date())
                                    .build(),
                            Prize.builder()
                                    .name("Legendary Staff")
                                    .description("A staff with immense magical power.")
                                    .imageUrl("/images/prizes/legendary_staff.png")
                                    .tier(legendary)
                                    .createdAt(new Date())
                                    .build()
                    )
            );
        }
    }
}

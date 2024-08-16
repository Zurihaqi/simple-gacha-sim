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
                                    .imageUrl("https://qph.cf2.quoracdn.net/main-qimg-94cc8cb7dc064fa3b3ece83cef529a25")
                                    .tier(common)
                                    .createdAt(new Date())
                                    .build(),
                            Prize.builder()
                                    .name("Uncommon Shield")
                                    .description("A shield with some magical properties.")
                                    .imageUrl("https://64.media.tumblr.com/8dcb08c7f1edea651ed3dd56feced753/18468924f9f6c556-2c/s1280x1920/a0652a483e79a091dd61728e887ff3decabf6275.jpg")
                                    .tier(uncommon)
                                    .createdAt(new Date())
                                    .build(),
                            Prize.builder()
                                    .name("Rare Armor")
                                    .description("Armor that provides excellent protection.")
                                    .imageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRZWNT3pIEo5_Cvvva7eVJqIP9EoH9WY00nTg&s")
                                    .tier(rare)
                                    .createdAt(new Date())
                                    .build(),
                            Prize.builder()
                                    .name("Epic Bow")
                                    .description("A bow that never misses its target.")
                                    .imageUrl("https://static.wikia.nocookie.net/epicbattlefantasy/images/9/9e/Sharanga_5.png/revision/latest/smart/width/250/height/250?cb=20171024094807")
                                    .tier(epic)
                                    .createdAt(new Date())
                                    .build(),
                            Prize.builder()
                                    .name("Legendary Staff")
                                    .description("A staff with immense magical power.")
                                    .imageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRv2nvG_pT5Bjrp6Gr_ViMsKpfDfuOnlN4plg&s")
                                    .tier(legendary)
                                    .createdAt(new Date())
                                    .build()
                    )
            );
        }
    }
}

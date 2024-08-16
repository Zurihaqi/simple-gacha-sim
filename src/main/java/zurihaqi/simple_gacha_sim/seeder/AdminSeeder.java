package zurihaqi.simple_gacha_sim.seeder;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import zurihaqi.simple_gacha_sim.model.Inventory;
import zurihaqi.simple_gacha_sim.model.User;
import zurihaqi.simple_gacha_sim.repository.InventoryRepository;
import zurihaqi.simple_gacha_sim.repository.UserRepository;

import java.util.Date;

@RequiredArgsConstructor
@Component
@Transactional
public class AdminSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final InventoryRepository inventoryRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        seedAdmins();
    }

    private void seedAdmins(){
        String adminEmail = "admin@mail.com";
        String adminPassword = "admin1234";
        if(userRepository.findByEmail(adminEmail).isPresent()) return;

        inventoryRepository.save(
                Inventory.builder()
                        .createdAt(new Date())
                        .updatedAt(null)
                        .build()
        );

        userRepository.save(
                User.builder()
                        .email(adminEmail)
                        .username("Admin")
                        .password(passwordEncoder.encode(adminPassword))
                        .role(User.Role.ADMIN)
                        .createdAt(new Date())
                        .build()
        );
    }
}

package zurihaqi.simple_gacha_sim.service.implementation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import zurihaqi.simple_gacha_sim.dto.PrizeDTO;
import zurihaqi.simple_gacha_sim.model.Inventory;
import zurihaqi.simple_gacha_sim.model.InventoryPrize;
import zurihaqi.simple_gacha_sim.model.Prize;
import zurihaqi.simple_gacha_sim.model.User;
import zurihaqi.simple_gacha_sim.repository.InventoryPrizeRepository;
import zurihaqi.simple_gacha_sim.repository.InventoryRepository;
import zurihaqi.simple_gacha_sim.repository.PrizeRepository;
import zurihaqi.simple_gacha_sim.service.GachaService;
import zurihaqi.simple_gacha_sim.utils.ObjectMapper;

import java.util.*;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class GachaServiceImpl implements GachaService {
    private final PrizeRepository prizeRepository;
    private final InventoryRepository inventoryRepository;
    private final InventoryPrizeRepository inventoryPrizeRepository;

    @Override
    @Transactional
    public PrizeDTO pullOnePrize() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<PrizeDTO> prizeDTOs = prizeRepository.findAll().stream().map(ObjectMapper::toPrizeDTO).toList();
        double[] dropRates = prizeDTOs.stream().mapToDouble(PrizeDTO::getDropRate).toArray();

        PrizeDTO pulledPrizeDTO = getPrizeByDropRate(prizeDTOs, dropRates);

        Prize pulledPrize = prizeRepository.findById(pulledPrizeDTO.getId())
                .orElseThrow(() -> new RuntimeException("Prize not found"));

        Inventory inventory = inventoryRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    Inventory newInventory = Inventory.builder()
                            .user(user)
                            .createdAt(new Date())
                            .updatedAt(new Date())
                            .build();
                    return inventoryRepository.save(newInventory);
                });

        InventoryPrize inventoryPrize = inventory.getInventoryPrizes().stream()
                .filter(ip -> ip.getPrize().equals(pulledPrize))
                .findFirst()
                .orElse(null);

        if (inventoryPrize == null) {
            inventoryPrize = InventoryPrize.builder()
                    .inventory(inventory)
                    .prize(pulledPrize)
                    .amount(1)
                    .createdAt(new Date())
                    .updatedAt(new Date())
                    .build();
            inventory.getInventoryPrizes().add(inventoryPrize);
        } else {
            inventoryPrize.setAmount(inventoryPrize.getAmount() + 1);
            inventoryPrize.setUpdatedAt(new Date());
        }

        inventoryPrizeRepository.save(inventoryPrize);

        return pulledPrizeDTO;
    }

    @Override
    @Transactional
    public Page<PrizeDTO> pullManyPrizes(Pageable pageable, Integer count) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<PrizeDTO> prizes = prizeRepository.findAll().stream().map(ObjectMapper::toPrizeDTO).toList();
        double[] dropRates = prizes.stream().mapToDouble(PrizeDTO::getDropRate).toArray();

        List<PrizeDTO> pulledPrizes = new ArrayList<>();
        Inventory inventory = inventoryRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    Inventory newInventory = Inventory.builder()
                            .user(user)
                            .createdAt(new Date())
                            .updatedAt(new Date())
                            .build();
                    return inventoryRepository.save(newInventory);
                });

        for (int i = 0; i < count; i++) {
            PrizeDTO pulledPrize = getPrizeByDropRate(prizes, dropRates);
            Prize prizeEntity = prizeRepository.findById(pulledPrize.getId())
                    .orElseThrow(() -> new RuntimeException("Prize not found"));

            InventoryPrize inventoryPrize = inventory.getInventoryPrizes().stream()
                    .filter(ip -> ip.getPrize().equals(prizeEntity))
                    .findFirst()
                    .orElse(null);

            if (inventoryPrize == null) {
                inventoryPrize = InventoryPrize.builder()
                        .inventory(inventory)
                        .prize(prizeEntity)
                        .amount(1)
                        .createdAt(new Date())
                        .updatedAt(new Date())
                        .build();
                inventory.getInventoryPrizes().add(inventoryPrize);
            } else {
                inventoryPrize.setAmount(inventoryPrize.getAmount() + 1);
                inventoryPrize.setUpdatedAt(new Date());
            }

            inventoryPrizeRepository.save(inventoryPrize);

            pulledPrizes.add(pulledPrize);
        }

        return new PageImpl<>(pulledPrizes, pageable, count);
    }

    private PrizeDTO getPrizeByDropRate(List<PrizeDTO> prizes, double[] dropRates) {
        double totalDropRate = Arrays.stream(dropRates).sum();
        double randomValue = new Random().nextDouble() * totalDropRate;
        double cumulativeProbability = 0.0;

        for (int i = 0; i < dropRates.length; i++) {
            cumulativeProbability += dropRates[i];
            if (randomValue <= cumulativeProbability) {
                return prizes.get(i);
            }
        }

        return prizes.get(prizes.size() - 1);
    }
}

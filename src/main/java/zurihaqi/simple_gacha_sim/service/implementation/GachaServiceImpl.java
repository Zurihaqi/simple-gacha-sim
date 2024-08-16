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
import zurihaqi.simple_gacha_sim.model.Prize;
import zurihaqi.simple_gacha_sim.model.User;
import zurihaqi.simple_gacha_sim.repository.InventoryRepository;
import zurihaqi.simple_gacha_sim.repository.PrizeRepository;
import zurihaqi.simple_gacha_sim.service.GachaService;
import zurihaqi.simple_gacha_sim.utils.ObjectMapper;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GachaServiceImpl implements GachaService {
    private final PrizeRepository prizeRepository;
    private final InventoryRepository inventoryRepository;

    @Override
    @Transactional
    public PrizeDTO pullOnePrize() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<PrizeDTO> prizes = prizeRepository.findAll().stream().map(ObjectMapper::toPrizeDTO).toList();
        double[] dropRates = prizes.stream().mapToDouble(PrizeDTO::getDropRate).toArray();

        PrizeDTO pulledPrize = getPrizeByDropRate(prizes, dropRates);
        Prize prizeEntity = prizeRepository.findById(pulledPrize.getId())
                .orElseThrow(() -> new RuntimeException("Prize not found"));

        Inventory inventory = inventoryRepository.findByUserId(user.getId())
                .orElse(Inventory.builder().user(user).prizes(new HashSet<>()).createdAt(new Date()).build());

        inventory.getPrizes().add(prizeEntity);
        inventoryRepository.save(inventory);

        return pulledPrize;
    }

    @Override
    @Transactional
    public Page<PrizeDTO> pullManyPrizes(Pageable pageable, Integer count) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<PrizeDTO> prizes = prizeRepository.findAll().stream().map(ObjectMapper::toPrizeDTO).toList();
        double[] dropRates = prizes.stream().mapToDouble(PrizeDTO::getDropRate).toArray();

        List<PrizeDTO> pulledPrizes = new ArrayList<>();
        Inventory inventory = inventoryRepository.findByUserId(user.getId())
                .orElse(Inventory.builder().user(user).prizes(new HashSet<>()).createdAt(new Date()).build());

        for (int i = 0; i < count; i++) {
            PrizeDTO pulledPrize = getPrizeByDropRate(prizes, dropRates);
            Prize prizeEntity = prizeRepository.findById(pulledPrize.getId())
                    .orElseThrow(() -> new RuntimeException("Prize not found"));

            pulledPrizes.add(pulledPrize);
            inventory.getPrizes().add(prizeEntity);
        }

        inventoryRepository.save(inventory);

        return new PageImpl<>(pulledPrizes, pageable, count);
    }


    private PrizeDTO getPrizeByDropRate(List<PrizeDTO> prizes, double[] dropRates) {
        double totalWeight = 0.0;
        for (double rate : dropRates) {
            totalWeight += rate;
        }

        double randomValue = Math.random() * totalWeight;

        for (int i = 0; i < prizes.size(); i++) {
            randomValue -= dropRates[i];
            if (randomValue <= 0.0) {
                return prizes.get(i);
            }
        }

        return prizes.get(0);
    }

}

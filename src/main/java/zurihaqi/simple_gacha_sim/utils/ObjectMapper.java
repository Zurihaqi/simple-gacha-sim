package zurihaqi.simple_gacha_sim.utils;

import zurihaqi.simple_gacha_sim.dto.InventoryDTO;
import zurihaqi.simple_gacha_sim.dto.PrizeDTO;
import zurihaqi.simple_gacha_sim.dto.UserDTO;
import zurihaqi.simple_gacha_sim.model.Inventory;
import zurihaqi.simple_gacha_sim.model.Prize;
import zurihaqi.simple_gacha_sim.model.User;
import zurihaqi.simple_gacha_sim.model.InventoryPrize;

import java.util.Map;
import java.util.stream.Collectors;

public class ObjectMapper {
    public static UserDTO toUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getActualUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public static PrizeDTO toPrizeDTO(Prize prize) {
        return PrizeDTO.builder()
                .id(prize.getId())
                .name(prize.getName())
                .description(prize.getDescription())
                .imageUrl(prize.getImageUrl())
                .createdAt(prize.getCreatedAt())
                .updatedAt(prize.getUpdatedAt())
                .tierId(prize.getTier().getId())
                .tierName(prize.getTier().getName())
                .dropRate(prize.getTier().getDropRate())
                .build();
    }

    public static InventoryDTO toInventoryDTO(Inventory inventory) {
        Map<Prize, Integer> prizeAmountMap = inventory.getInventoryPrizes().stream()
                .collect(Collectors.toMap(
                        InventoryPrize::getPrize,
                        InventoryPrize::getAmount
                ));

        return InventoryDTO.builder()
                .id(inventory.getId())
                .createdAt(inventory.getCreatedAt())
                .updatedAt(inventory.getUpdatedAt())
                .userId(inventory.getUser().getId())
                .ownerName(inventory.getUser().getActualUsername())
                .prizes(prizeAmountMap.entrySet().stream()
                        .map(entry -> PrizeDTO.builder()
                                .id(entry.getKey().getId())
                                .name(entry.getKey().getName())
                                .description(entry.getKey().getDescription())
                                .imageUrl(entry.getKey().getImageUrl())
                                .createdAt(entry.getKey().getCreatedAt())
                                .updatedAt(entry.getKey().getUpdatedAt())
                                .tierId(entry.getKey().getTier().getId())
                                .tierName(entry.getKey().getTier().getName())
                                .amount(entry.getValue())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
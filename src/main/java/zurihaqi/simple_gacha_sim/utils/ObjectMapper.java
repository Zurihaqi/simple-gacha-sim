package zurihaqi.simple_gacha_sim.utils;

import zurihaqi.simple_gacha_sim.dto.InventoryDTO;
import zurihaqi.simple_gacha_sim.dto.PrizeDTO;
import zurihaqi.simple_gacha_sim.dto.UserDTO;
import zurihaqi.simple_gacha_sim.model.Inventory;
import zurihaqi.simple_gacha_sim.model.Prize;
import zurihaqi.simple_gacha_sim.model.User;

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
        return InventoryDTO.builder()
                .id(inventory.getId())
                .createdAt(inventory.getCreatedAt())
                .updatedAt(inventory.getUpdatedAt())
                .userId(inventory.getUser().getId())
                .ownerName(inventory.getUser().getActualUsername())
                .prizes(inventory.getPrizes().stream()
                        .map(ObjectMapper::toPrizeDTO)
                        .toList())
                .build();
    }
}

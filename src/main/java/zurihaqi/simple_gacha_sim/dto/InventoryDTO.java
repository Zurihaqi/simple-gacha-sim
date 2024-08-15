package zurihaqi.simple_gacha_sim.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class InventoryDTO {
    private Long id;
    private Date createdAt;
    private Date updatedAt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long userId;
    private String ownerName;
    private List<PrizeDTO> prizes;
}

package zurihaqi.simple_gacha_sim.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class PrizeDTO {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date createdAt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date updatedAt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long tierId;
    private String tierName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double dropRate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer amount;
}

package zurihaqi.simple_gacha_sim.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity(name = "inventory_prizes")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryPrize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "inventory_id", referencedColumnName = "id")
    private Inventory inventory;

    @ManyToOne
    @JoinColumn(name = "prize_id", referencedColumnName = "id")
    private Prize prize;

    private Integer amount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}

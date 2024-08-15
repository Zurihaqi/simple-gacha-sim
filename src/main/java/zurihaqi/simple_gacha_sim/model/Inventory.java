package zurihaqi.simple_gacha_sim.model;

import jakarta.persistence.*;
import lombok.*;
import zurihaqi.simple_gacha_sim.dto.PrizeDTO;

import java.util.Date;
import java.util.Set;

@Entity(name = "inventories")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "inventory_prizes",
            joinColumns = @JoinColumn(name = "inventory_id"),
            inverseJoinColumns = @JoinColumn(name = "prize_id")
    )
    private Set<Prize> prizes;
}

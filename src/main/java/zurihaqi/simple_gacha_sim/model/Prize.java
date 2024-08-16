package zurihaqi.simple_gacha_sim.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "prizes")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Prize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String imageUrl;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "tier_id", referencedColumnName = "id")
    private Tier tier;

    @OneToMany(mappedBy = "prize", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private Set<InventoryPrize> inventoryPrizes = new HashSet<>();
}

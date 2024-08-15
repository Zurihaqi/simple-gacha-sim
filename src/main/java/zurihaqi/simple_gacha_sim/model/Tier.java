package zurihaqi.simple_gacha_sim.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity(name = "tiers")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private Double dropRate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @OneToMany(mappedBy = "tier", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private List<Prize> prizes;
}

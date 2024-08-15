package zurihaqi.simple_gacha_sim.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import zurihaqi.simple_gacha_sim.model.User;

import java.util.Date;

@Getter
@Setter
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private User.Role role;
    private Date createdAt;
    private Date updatedAt;
}

package zurihaqi.simple_gacha_sim.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zurihaqi.simple_gacha_sim.dto.UserDTO;
import zurihaqi.simple_gacha_sim.model.User;

public interface UserService {
    UserDTO updateUser(Long id, User user);

    Page<UserDTO> getAllUsers(Pageable pageable);

    UserDTO getOneUser(Long id);

    void deleteUser(Long id);
}

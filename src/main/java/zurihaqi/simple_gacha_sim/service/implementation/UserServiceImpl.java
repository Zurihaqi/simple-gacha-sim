package zurihaqi.simple_gacha_sim.service.implementation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zurihaqi.simple_gacha_sim.dto.UserDTO;
import zurihaqi.simple_gacha_sim.model.User;
import zurihaqi.simple_gacha_sim.repository.UserRepository;
import zurihaqi.simple_gacha_sim.service.UserService;
import zurihaqi.simple_gacha_sim.utils.ObjectMapper;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDTO updateUser(Long id, User user) {
        User newUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        if(user.getActualUsername() != null) newUser.setUsername(user.getActualUsername());
        if(user.getEmail() != null) newUser.setEmail(user.getEmail());
        if(user.getRole() != null) newUser.setRole(user.getRole());
        newUser.setUpdatedAt(new Date());
        userRepository.save(newUser);

        return ObjectMapper.toUserDTO(newUser);
    }

    @Override
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        List<UserDTO> mappedUser = userRepository.findAll(pageable).stream().map(ObjectMapper::toUserDTO).toList();
        return new PageImpl<>(mappedUser, pageable, userRepository.count());
    }

    @Override
    public UserDTO getOneUser(Long id) {
        return ObjectMapper.toUserDTO(userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.deleteById(id);
    }
}

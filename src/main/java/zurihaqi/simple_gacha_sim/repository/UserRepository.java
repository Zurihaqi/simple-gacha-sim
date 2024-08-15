package zurihaqi.simple_gacha_sim.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zurihaqi.simple_gacha_sim.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository  {
    List<User> findAll();

    Page<User> findAll(Pageable pageable);

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    User save(User user);

    void deleteById(Long id);

    long count();
}

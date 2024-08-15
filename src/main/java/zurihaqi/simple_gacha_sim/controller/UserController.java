package zurihaqi.simple_gacha_sim.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zurihaqi.simple_gacha_sim.model.User;
import zurihaqi.simple_gacha_sim.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @RequestMapping
    public ResponseEntity<?> getAllUsers(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }

    @RequestMapping("/{id}")
    public ResponseEntity<?> getOneUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getOneUser(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "Success");
        response.put("message", "User deleted");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

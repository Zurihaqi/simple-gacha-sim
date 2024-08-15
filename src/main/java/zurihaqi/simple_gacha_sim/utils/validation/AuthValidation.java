package zurihaqi.simple_gacha_sim.utils.validation;

import zurihaqi.simple_gacha_sim.dto.AuthDTO;

public class AuthValidation {
    public static void validateAuthDTO(AuthDTO authDTO, boolean isRegistration) {
        if(authDTO.getPassword() == null || authDTO.getPassword().isBlank()) {
            throw new RuntimeException("Password cannot be empty");
        }

        if(authDTO.getEmail() == null || authDTO.getEmail().isBlank()) {
            throw new RuntimeException("Email cannot be empty");
        }

        if (isRegistration) {
            if(authDTO.getUsername() == null || authDTO.getUsername().isBlank()) {
                throw new RuntimeException("Username cannot be empty");
            }
        }
    }
}

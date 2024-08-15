package zurihaqi.simple_gacha_sim.service;

import zurihaqi.simple_gacha_sim.dto.AuthDTO;

public interface AuthService {
    AuthDTO login(AuthDTO authDTO);

    AuthDTO register(AuthDTO authDTO);
}

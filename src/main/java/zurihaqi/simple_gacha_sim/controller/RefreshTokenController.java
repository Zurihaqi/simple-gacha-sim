package zurihaqi.simple_gacha_sim.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zurihaqi.simple_gacha_sim.dto.AuthDTO;
import zurihaqi.simple_gacha_sim.service.RefreshTokenService;

@RestController
@RequestMapping(path = "/api/v1/refresh-token")
@RequiredArgsConstructor
public class RefreshTokenController {
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody AuthDTO authDTO) {
        return new ResponseEntity<>(refreshTokenService.refreshToken(authDTO.getRefreshToken()), HttpStatus.OK);
    }
}

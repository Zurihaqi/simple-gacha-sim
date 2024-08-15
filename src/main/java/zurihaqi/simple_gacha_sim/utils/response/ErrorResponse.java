package zurihaqi.simple_gacha_sim.utils.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErrorResponse {
    public static <T> ResponseEntity<?> renderJsonResponse(String message, RuntimeException ex, HttpStatus httpStatus) {
        WebResponse<T> webResponse = WebResponse.<T>builder()
                .message(message)
                .status(httpStatus.name())
                .error(ex.toString())
                .build();

        return ResponseEntity.status(httpStatus).body(webResponse);
    }
}

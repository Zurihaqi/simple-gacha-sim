package zurihaqi.simple_gacha_sim.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import zurihaqi.simple_gacha_sim.utils.response.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleException(RuntimeException e) {
        return ErrorResponse.renderJsonResponse(e.getMessage(), e, HttpStatus.BAD_REQUEST);
    }
}

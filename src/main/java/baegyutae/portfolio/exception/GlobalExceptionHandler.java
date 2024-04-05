package baegyutae.portfolio.exception;

import baegyutae.portfolio.response.ApiError;
import baegyutae.portfolio.response.ApiResponse;
import java.util.Collections;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotAuthenticatedException.class)
    public ResponseEntity<ApiResponse<ApiError>> handleUserNotAuthenticated(UserNotAuthenticatedException ex) {
        ApiError apiError = ApiError.builder()
            .message("인증되지 않은 사용자입니다.")
            .errors(Collections.singletonList(ex.getMessage()))
            .build();

        ApiResponse<ApiError> response = ApiResponse.<ApiError>builder()
            .data(apiError)
            .build();

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<ApiError>> handleAllExceptions(
        Exception ex) {

        ApiError apiError = ApiError.builder()
            .message(ex.getMessage())
            .errors(Collections.singletonList("오류가 발생했습니다."))
            .build();

        ApiResponse<ApiError> response = ApiResponse.<ApiError>builder()
            .data(apiError)
            .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

package baegyutae.portfolio.exception;

import baegyutae.portfolio.response.ApiError;
import baegyutae.portfolio.response.ApiResponse;
import java.util.Collections;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotAuthenticatedException.class)
    public ResponseEntity<ApiResponse<ApiError>> handleUserNotAuthenticated(UserNotAuthenticatedException ex) {
        ApiError apiError = new ApiError("인증되지 않은 사용자입니다.", Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(ApiResponse.error(apiError), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFound(ResourceNotFoundException ex) {
        ApiResponse<Object> response = ApiResponse.error(new ApiError("리소스를 찾을 수 없습니다.", List.of(ex.getMessage())));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataValidationException.class)
    public ResponseEntity<ApiResponse<Object>> handleDataValidationException(DataValidationException ex) {
        ApiResponse<Object> response = ApiResponse.error(new ApiError("데이터 검증 실패", List.of(ex.getMessage())));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<ApiError>> handleAllExceptions(Exception ex) {
        ApiError apiError = new ApiError("내부 서버 오류가 발생했습니다.", Collections.singletonList("오류가 발생했습니다."));
        return new ResponseEntity<>(ApiResponse.error(apiError), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(LoginFailureException.class)
    public ResponseEntity<ApiResponse<Object>> handleLoginFailure(LoginFailureException ex) {
        ApiError apiError = new ApiError("로그인 실패", Collections.singletonList(ex.getMessage()));
        ApiResponse<Object> apiResponse = ApiResponse.error(apiError);
        return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<List<String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
            .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
            .toList();

        ApiResponse<List<String>> response = ApiResponse.error(new ApiError("유효성 검증 오류", errors));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}

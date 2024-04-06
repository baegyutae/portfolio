package baegyutae.portfolio.controller;

import baegyutae.portfolio.dto.SignupRequestDto;
import baegyutae.portfolio.dto.SignupResponseDto;
import baegyutae.portfolio.dto.UserLoginDto;
import baegyutae.portfolio.response.ApiResponse;
import baegyutae.portfolio.service.UserService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> registerUser(
        @Valid @RequestBody SignupRequestDto signupRequestDto) {
        SignupResponseDto responseDto = userService.signupUser(signupRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, String>>> loginUser(
        @RequestBody UserLoginDto loginDto) throws Exception {
        String token = userService.loginUser(loginDto);
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(ApiResponse.success(response));
    }


}

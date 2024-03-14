package baegyutae.portfolio.controller;

import baegyutae.portfolio.dto.SignupRequestDto;
import baegyutae.portfolio.dto.SignupResponseDto;
import baegyutae.portfolio.dto.UserLoginDto;
import baegyutae.portfolio.service.UserService;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
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
    public ResponseEntity<?> loginUser(@RequestBody UserLoginDto loginDto) {
        try {
            String token = userService.loginUser(loginDto);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token); // JWT 토큰을 헤더에 추가

            return new ResponseEntity<>(headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "로그인 실패"));
        }
    }

}

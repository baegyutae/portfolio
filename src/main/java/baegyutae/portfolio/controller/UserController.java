package baegyutae.portfolio.controller;

import baegyutae.portfolio.dto.UserLoginDto;
import baegyutae.portfolio.dto.UserRegistrationDto;
import baegyutae.portfolio.service.UserService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDto registrationDto) {
        return ResponseEntity.ok(userService.registerUser(registrationDto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginDto loginDto) {
        return ResponseEntity.ok(userService.loginUser(loginDto));
    }

}

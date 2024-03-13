package baegyutae.portfolio.service.impl;

import baegyutae.portfolio.dto.SignupRequestDto;
import baegyutae.portfolio.dto.SignupResponseDto;
import baegyutae.portfolio.dto.UserLoginDto;
import baegyutae.portfolio.dto.UserResponseDto;
import baegyutae.portfolio.entity.User;
import baegyutae.portfolio.repository.UserRepository;
import baegyutae.portfolio.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto loginUser(UserLoginDto loginDto) {
        User user = userRepository.findByUsername(loginDto.username());
        if (user != null && passwordEncoder.matches(loginDto.password(), user.getPassword())) {
            return new UserResponseDto(user.getId(), user.getUsername());
        } else {
            throw new RuntimeException("Login failed.");
        }
    }

    @Override
    public SignupResponseDto signupUser(SignupRequestDto signupRequestDto) {
        if (userRepository.existsByUsername(signupRequestDto.username())
            || userRepository.existsByEmail(signupRequestDto.email())) {
            throw new IllegalStateException("Username or email already in use");
        }

        User newUser = User.builder()
            .username(signupRequestDto.username())
            .email(signupRequestDto.email())
            .password(passwordEncoder.encode(signupRequestDto.password()))
            .build();

        User savedUser = userRepository.save(newUser);

        return new SignupResponseDto(savedUser.getId(), savedUser.getUsername(),
            savedUser.getEmail());
    }

}

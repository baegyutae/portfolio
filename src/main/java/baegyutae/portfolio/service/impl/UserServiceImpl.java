package baegyutae.portfolio.service.impl;

import baegyutae.portfolio.dto.UserLoginDto;
import baegyutae.portfolio.dto.UserRegistrationDto;
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
    public UserResponseDto registerUser(UserRegistrationDto registrationDto) {
        User user = User.builder()
            .username(registrationDto.username())
            .password(passwordEncoder.encode(registrationDto.password()))
            .build();
        userRepository.save(user);
        return new UserResponseDto(user.getId(), user.getUsername());
    }

    @Override
    public UserResponseDto loginUser(UserLoginDto loginDto) {
        User user = userRepository.findByUsername(loginDto.username());
        if(user != null && passwordEncoder.matches(loginDto.password(), user.getPassword())) {
            return new UserResponseDto(user.getId(), user.getUsername());
        } else {
            throw new RuntimeException("Login failed.");
        }
    }
}

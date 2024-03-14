package baegyutae.portfolio.service.impl;

import baegyutae.portfolio.dto.SignupRequestDto;
import baegyutae.portfolio.dto.SignupResponseDto;
import baegyutae.portfolio.dto.UserLoginDto;
import baegyutae.portfolio.entity.User;
import baegyutae.portfolio.repository.UserRepository;
import baegyutae.portfolio.security.JwtTokenUtil;
import baegyutae.portfolio.service.UserService;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public String loginUser(UserLoginDto loginDto) {
        return userRepository.findByUsername(loginDto.username())
            .map(user -> {
                if (!passwordEncoder.matches(loginDto.password(), user.getPassword())) {
                    throw new RuntimeException("비밀번호가 일치하지 않습니다.");
                }
                final UserDetails userDetails = loadUserByUsername(loginDto.username());
                return jwtTokenUtil.generateToken(userDetails);
            })
            .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
            .map(user -> new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>()))
            .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    @Override
    public SignupResponseDto signupUser(SignupRequestDto signupRequestDto) {
        if (userRepository.existsByUsername(signupRequestDto.username()) || userRepository.existsByEmail(signupRequestDto.email())) {
            throw new IllegalStateException("Username or email already in use");
        }

        User newUser = User.builder()
            .username(signupRequestDto.username())
            .email(signupRequestDto.email())
            .password(passwordEncoder.encode(signupRequestDto.password()))
            .build();

        User savedUser = userRepository.save(newUser);

        return new SignupResponseDto(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail());
    }
}

package baegyutae.portfolio.service.impl;

import static baegyutae.portfolio.constant.Constants.PASSWORD_MISMATCH_ERROR;
import static baegyutae.portfolio.constant.Constants.USERNAME_OR_EMAIL_ALREADY_IN_USE;
import static baegyutae.portfolio.constant.Constants.USER_NOT_FOUND_ERROR;
import static baegyutae.portfolio.constant.Constants.USER_NOT_FOUND_WITH_USERNAME;

import baegyutae.portfolio.dto.user.SignupRequestDto;
import baegyutae.portfolio.dto.user.SignupResponseDto;
import baegyutae.portfolio.dto.user.UserLoginDto;
import baegyutae.portfolio.entity.User;
import baegyutae.portfolio.exception.PasswordMismatchException;
import baegyutae.portfolio.exception.UserNotFoundException;
import baegyutae.portfolio.exception.UsernameOrEmailAlreadyInUseException;
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
                    throw new PasswordMismatchException(PASSWORD_MISMATCH_ERROR);
                }
                final UserDetails userDetails = loadUserByUsername(loginDto.username());
                return jwtTokenUtil.generateToken(userDetails);
            })
            .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_ERROR));
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
            .map(user -> new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>()))
            .orElseThrow(() -> new UsernameNotFoundException(
                String.format(USER_NOT_FOUND_WITH_USERNAME, username)));
    }

    @Override
    public SignupResponseDto signupUser(SignupRequestDto signupRequestDto) {
        if (userRepository.existsByUsername(signupRequestDto.username())
            || userRepository.existsByEmail(signupRequestDto.email())) {
            throw new UsernameOrEmailAlreadyInUseException(USERNAME_OR_EMAIL_ALREADY_IN_USE);
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

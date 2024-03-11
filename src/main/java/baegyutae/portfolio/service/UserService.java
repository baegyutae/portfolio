package baegyutae.portfolio.service;

import baegyutae.portfolio.dto.UserLoginDto;
import baegyutae.portfolio.dto.UserRegistrationDto;
import baegyutae.portfolio.dto.UserResponseDto;

public interface UserService {
    UserResponseDto registerUser(UserRegistrationDto registrationDto);
    UserResponseDto loginUser(UserLoginDto loginDto);
}

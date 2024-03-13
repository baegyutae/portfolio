package baegyutae.portfolio.service;

import baegyutae.portfolio.dto.SignupRequestDto;
import baegyutae.portfolio.dto.SignupResponseDto;
import baegyutae.portfolio.dto.UserLoginDto;
import baegyutae.portfolio.dto.UserResponseDto;

public interface UserService {

    SignupResponseDto signupUser(SignupRequestDto signupRequestDto);

    UserResponseDto loginUser(UserLoginDto loginDto);
}

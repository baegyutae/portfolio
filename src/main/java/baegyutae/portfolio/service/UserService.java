package baegyutae.portfolio.service;

import baegyutae.portfolio.dto.user.SignupRequestDto;
import baegyutae.portfolio.dto.user.SignupResponseDto;
import baegyutae.portfolio.dto.user.UserLoginDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {

    // UserDetails 반환
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    SignupResponseDto signupUser(SignupRequestDto signupRequestDto);

    String loginUser(UserLoginDto loginDto) throws Exception;
}

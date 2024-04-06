package baegyutae.portfolio.dto;

import jakarta.validation.constraints.NotBlank;

public record UserLoginDto(
    @NotBlank(message = "사용자 이름은 필수입니다.")
    String username,

    @NotBlank(message = "비밀번호는 필수입니다.")
    String password
) {

}


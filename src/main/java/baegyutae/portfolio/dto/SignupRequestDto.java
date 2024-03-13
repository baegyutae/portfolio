package baegyutae.portfolio.dto;

public record SignupRequestDto(
    String email,
    String username,
    String password
) {
}

package baegyutae.portfolio.dto.post;

import java.time.LocalDateTime;

public record PostResponseDto(
    Long id,
    String title,
    String content,
    String imageUrl,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

}

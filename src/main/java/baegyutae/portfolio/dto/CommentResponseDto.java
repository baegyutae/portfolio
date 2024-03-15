package baegyutae.portfolio.dto;

import java.time.LocalDateTime;

public record CommentResponseDto(
    Long id,
    String content,
    Long postId,
    String username,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

}

package baegyutae.portfolio.service;

import baegyutae.portfolio.dto.CommentCreateRequestDto;
import baegyutae.portfolio.dto.CommentResponseDto;

public interface CommentService {
    CommentResponseDto createComment(Long postId, Long userId, CommentCreateRequestDto requestDto);
}

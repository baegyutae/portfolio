package baegyutae.portfolio.service;

import baegyutae.portfolio.dto.CommentCreateRequestDto;
import baegyutae.portfolio.dto.CommentResponseDto;
import baegyutae.portfolio.dto.CommentUpdateRequestDto;
import java.util.List;

public interface CommentService {
    CommentResponseDto createComment(Long postId, Long userId, CommentCreateRequestDto requestDto);
    List<CommentResponseDto> findAllCommentsByPostId(Long postId);
    CommentResponseDto updateComment(Long commentId, CommentUpdateRequestDto requestDto);
}

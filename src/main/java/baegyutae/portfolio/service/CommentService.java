package baegyutae.portfolio.service;

import baegyutae.portfolio.dto.CommentCreateRequestDto;
import baegyutae.portfolio.dto.CommentResponseDto;
import baegyutae.portfolio.dto.CommentUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    CommentResponseDto createComment(Long postId, Long userId, CommentCreateRequestDto requestDto);
    CommentResponseDto updateComment(Long commentId, CommentUpdateRequestDto requestDto, Long userId);
    void deleteComment(Long commentId, Long userId);
    Page<CommentResponseDto> findAllCommentsByPostId(Long postId, Pageable pageable);
}

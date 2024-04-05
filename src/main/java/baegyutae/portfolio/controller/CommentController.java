package baegyutae.portfolio.controller;

import static baegyutae.portfolio.util.SecurityUtils.getCurrentUserId;

import baegyutae.portfolio.dto.CommentCreateRequestDto;
import baegyutae.portfolio.dto.CommentResponseDto;
import baegyutae.portfolio.dto.CommentUpdateRequestDto;
import baegyutae.portfolio.exception.UserNotAuthenticatedException;
import baegyutae.portfolio.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(
        @PathVariable Long postId,
        @Valid @RequestBody CommentCreateRequestDto requestDto
    ) {
        Long userId = getCurrentUserId();
        if (userId == null) {
            throw new UserNotAuthenticatedException("인증된 사용자가 아닙니다.");
        }

        CommentResponseDto responseDto = commentService.createComment(postId, userId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<Page<CommentResponseDto>> getAllCommentsByPostId(
        @PathVariable Long postId, @PageableDefault(size = 5) Pageable pageable) {
        Page<CommentResponseDto> comments = commentService.findAllCommentsByPostId(postId,
            pageable);
        return ResponseEntity.ok(comments);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(
        @PathVariable Long postId,
        @PathVariable Long commentId,
        @RequestBody CommentUpdateRequestDto requestDto) {
        Long userId = getCurrentUserId();
        CommentResponseDto updatedComment = commentService.updateComment(commentId, requestDto, userId);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long postId,
        @PathVariable Long commentId) {
        Long userId = getCurrentUserId();
        commentService.deleteComment(commentId, userId);
        return ResponseEntity.noContent().build();
    }
}
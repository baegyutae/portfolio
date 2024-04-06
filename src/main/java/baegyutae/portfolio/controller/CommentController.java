package baegyutae.portfolio.controller;

import baegyutae.portfolio.dto.comment.CommentCreateRequestDto;
import baegyutae.portfolio.dto.comment.CommentResponseDto;
import baegyutae.portfolio.dto.comment.CommentUpdateRequestDto;
import baegyutae.portfolio.response.ApiResponse;
import baegyutae.portfolio.security.UserDetailsImpl;
import baegyutae.portfolio.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<ApiResponse<CommentResponseDto>> createComment(
        @PathVariable Long postId,
        @Valid @RequestBody CommentCreateRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Long userId = userDetails.getUser().getId();
        CommentResponseDto responseDto = commentService.createComment(postId, userId, requestDto);
        return new ResponseEntity<>(ApiResponse.success(responseDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<CommentResponseDto>>> getAllCommentsByPostId(
        @PathVariable Long postId, @PageableDefault(size = 5) Pageable pageable
    ) {
        Page<CommentResponseDto> comments = commentService.findAllCommentsByPostId(postId,
            pageable);
        return ResponseEntity.ok(ApiResponse.success(comments));
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<ApiResponse<CommentResponseDto>> updateComment(
        @PathVariable Long postId,
        @PathVariable Long commentId,
        @RequestBody CommentUpdateRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Long userId = userDetails.getUser().getId();
        CommentResponseDto updatedComment = commentService.updateComment(commentId, requestDto,
            userId);
        return ResponseEntity.ok(ApiResponse.success(updatedComment));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
        @PathVariable Long postId, @PathVariable Long commentId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Long userId = userDetails.getUser().getId();
        commentService.deleteComment(commentId, userId);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
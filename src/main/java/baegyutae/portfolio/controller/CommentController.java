package baegyutae.portfolio.controller;

import baegyutae.portfolio.dto.CommentCreateRequestDto;
import baegyutae.portfolio.dto.CommentResponseDto;
import baegyutae.portfolio.dto.CommentUpdateRequestDto;
import baegyutae.portfolio.entity.User;
import baegyutae.portfolio.security.UserDetailsImpl;
import baegyutae.portfolio.service.CommentService;
import jakarta.validation.Valid;
import java.util.List;
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
    public ResponseEntity<?> createComment(
        @PathVariable Long postId,
        @Valid @RequestBody CommentCreateRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        // UserDetailsImpl에서 사용자 정보 추출
        User user = userDetails.getUser();
        Long userId = user.getId(); // 혹은 userDetails.getId()와 같이 직접 ID를 얻는 메서드가 구현되어 있을 수 있음

        // 서비스 계층에 사용자 정보와 함께 댓글 생성 요청
        CommentResponseDto responseDto = commentService.createComment(postId, userId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getAllCommentsByPostId(
        @PathVariable Long postId) {
        List<CommentResponseDto> comments = commentService.findAllCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(
        @PathVariable Long postId,
        @PathVariable Long commentId,
        @RequestBody CommentUpdateRequestDto requestDto) {
        CommentResponseDto updatedComment = commentService.updateComment(commentId, requestDto);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long postId,
        @PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<CommentResponseDto>> getAllCommentsByPostId(
        @PathVariable Long postId, @PageableDefault(size = 5) Pageable pageable) {
        Page<CommentResponseDto> comments = commentService.findAllCommentsByPostId(postId, pageable);
        return ResponseEntity.ok(comments);
    }
}

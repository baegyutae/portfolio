package baegyutae.portfolio.controller;

import baegyutae.portfolio.dto.CommentCreateRequestDto;
import baegyutae.portfolio.dto.CommentResponseDto;
import baegyutae.portfolio.service.CommentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(
        @PathVariable Long postId,
        @RequestParam("userId") Long userId,
        @RequestBody CommentCreateRequestDto requestDto) {
        CommentResponseDto createdComment = commentService.createComment(postId, userId,
            requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getAllCommentsByPostId(@PathVariable Long postId) {
        List<CommentResponseDto> comments = commentService.findAllCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }
}

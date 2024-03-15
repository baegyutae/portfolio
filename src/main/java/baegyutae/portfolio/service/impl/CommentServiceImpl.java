package baegyutae.portfolio.service.impl;

import baegyutae.portfolio.dto.CommentCreateRequestDto;
import baegyutae.portfolio.dto.CommentResponseDto;
import baegyutae.portfolio.dto.CommentUpdateRequestDto;
import baegyutae.portfolio.entity.Comment;
import baegyutae.portfolio.entity.Post;
import baegyutae.portfolio.entity.User;
import baegyutae.portfolio.repository.CommentRepository;
import baegyutae.portfolio.repository.PostRepository;
import baegyutae.portfolio.repository.UserRepository;
import baegyutae.portfolio.service.CommentService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public CommentResponseDto createComment(Long postId, Long userId,
        CommentCreateRequestDto requestDto) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + postId));
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        Comment newComment = Comment.builder()
            .content(requestDto.content())
            .post(post)
            .user(user)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        Comment savedComment = commentRepository.save(newComment);

        return new CommentResponseDto(
            savedComment.getId(),
            savedComment.getContent(),
            post.getId(),
            user.getUsername(),
            savedComment.getCreatedAt(),
            savedComment.getUpdatedAt()
        );
    }

    @Override
    public List<CommentResponseDto> findAllCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream()
            .map(comment -> new CommentResponseDto(
                comment.getId(),
                comment.getContent(),
                comment.getPost().getId(),
                comment.getUser().getUsername(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()))
            .toList();
    }

    @Override
    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentUpdateRequestDto requestDto) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(
                () -> new IllegalArgumentException("Comment not found with id: " + commentId));

        comment.updateContent(requestDto.content());

        return new CommentResponseDto(
            comment.getId(),
            comment.getContent(),
            comment.getPost().getId(),
            comment.getUser().getUsername(),
            comment.getCreatedAt(),
            comment.getUpdatedAt()
        );
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new IllegalArgumentException("Comment not found with id: " + commentId));
        commentRepository.delete(comment);
    }

}

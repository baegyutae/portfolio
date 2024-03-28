package baegyutae.portfolio.repository;

import baegyutae.portfolio.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 댓글 페이지 네이션
    Page<Comment> findByPostId(Long postId, Pageable pageable);
}

package baegyutae.portfolio.repository;

import baegyutae.portfolio.entity.Comment;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 특정 게시글에 대한 댓글 목록 조회
    List<Comment> findByPostId(Long postId);

    // 댓글 페이지 네이션
    Page<Comment> findByPostId(Long postId, Pageable pageable);
}

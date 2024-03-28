package baegyutae.portfolio.repository;

import baegyutae.portfolio.entity.Post;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.comments c LEFT JOIN FETCH c.user WHERE p.id = :postId")
    Optional<Post> findByIdWithCommentsAndUser(@Param("postId") Long postId);
}

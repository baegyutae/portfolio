package baegyutae.portfolio.repository;

import baegyutae.portfolio.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}

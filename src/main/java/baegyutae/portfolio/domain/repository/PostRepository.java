package baegyutae.portfolio.domain.repository;

import baegyutae.portfolio.domain.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}

package baegyutae.portfolio.mapper;

import baegyutae.portfolio.dto.post.PostResponseDto;
import baegyutae.portfolio.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public PostResponseDto toPostResponseDto(Post post) {
        return new PostResponseDto(
            post.getId(),
            post.getTitle(),
            post.getContent(),
            post.getImageUrl(),
            post.getCreatedAt(),
            post.getUpdatedAt()
        );
    }
}
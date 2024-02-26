package baegyutae.portfolio.service;

import baegyutae.portfolio.dto.PostCreateDto;
import baegyutae.portfolio.dto.PostResponseDto;
import java.util.List;

public interface PostService {

    Long createPost(PostCreateDto postCreateDto);
    List<PostResponseDto> getAllPosts();
    PostResponseDto getPostById(Long id);
}

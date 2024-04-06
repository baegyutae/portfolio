package baegyutae.portfolio.service;

import baegyutae.portfolio.dto.post.PostCreateDto;
import baegyutae.portfolio.dto.post.PostResponseDto;
import baegyutae.portfolio.dto.post.PostUpdateDto;
import java.util.List;

public interface PostService {

    PostResponseDto createPost(PostCreateDto postCreateDto);
    List<PostResponseDto> getAllPosts();
    PostResponseDto getPostById(Long id);
    void updatePost(Long id, PostUpdateDto postUpdateDto);
    void deletePost(Long id);
}

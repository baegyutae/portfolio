package baegyutae.portfolio.service.impl;

import baegyutae.portfolio.domain.model.Post;
import baegyutae.portfolio.domain.repository.PostRepository;
import baegyutae.portfolio.dto.PostCreateDto;
import baegyutae.portfolio.dto.PostResponseDto;
import baegyutae.portfolio.service.PostService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Transactional
    @Override
    public Long createPost(PostCreateDto postCreateDto) {
        Post post = Post.builder()
            .title(postCreateDto.title())
            .content(postCreateDto.content())
            .build();
        postRepository.save(post);
        return post.getId();
    }

    @Transactional(readOnly = true)
    @Override
    public List<PostResponseDto> getAllPosts() {
        return postRepository.findAll().stream()
            .map(post -> new PostResponseDto(post.getId(), post.getTitle(), post.getContent(),
                post.getCreatedAt(), post.getUpdatedAt()))
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public PostResponseDto getPostById(Long id) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + id));
        return new PostResponseDto(post.getId(), post.getTitle(), post.getContent(),
            post.getCreatedAt(), post.getUpdatedAt());
    }
}

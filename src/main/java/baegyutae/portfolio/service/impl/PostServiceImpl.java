package baegyutae.portfolio.service.impl;

import baegyutae.portfolio.domain.model.Post;
import baegyutae.portfolio.domain.repository.PostRepository;
import baegyutae.portfolio.dto.PostCreateDto;
import baegyutae.portfolio.dto.PostResponseDto;
import baegyutae.portfolio.dto.PostUpdateDto;
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
    public PostResponseDto createPost(PostCreateDto postCreateDto) {
        Post post = Post.builder()
            .title(postCreateDto.title())
            .content(postCreateDto.content())
            .imageUrl(postCreateDto.imageUrl())
            .build();
        post = postRepository.save(post);

        return new PostResponseDto(
            post.getId(),
            post.getTitle(),
            post.getContent(),
            post.getImageUrl(),
            post.getCreatedAt(),
            post.getUpdatedAt()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<PostResponseDto> getAllPosts() {
        return postRepository.findAll().stream()
            .map(post -> new PostResponseDto(post.getId(), post.getTitle(), post.getContent(), post.getImageUrl(),
                post.getCreatedAt(), post.getUpdatedAt()))
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public PostResponseDto getPostById(Long id) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + id));
        return new PostResponseDto(post.getId(), post.getTitle(), post.getContent(), post.getImageUrl(),
            post.getCreatedAt(), post.getUpdatedAt());
    }

    @Transactional
    @Override
    public void updatePost(Long id, PostUpdateDto postUpdateDto) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + id));
        post.update(postUpdateDto.title(), postUpdateDto.content(), postUpdateDto.imageUrl());
    }

    @Transactional
    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}

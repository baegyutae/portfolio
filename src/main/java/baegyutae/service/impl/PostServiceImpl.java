package baegyutae.service.impl;

import baegyutae.portfolio.domain.model.Post;
import baegyutae.portfolio.domain.repository.PostRepository;
import baegyutae.portfolio.dto.PostCreateDto;
import baegyutae.service.PostService;
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
}

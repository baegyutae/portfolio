package baegyutae.portfolio.service;

import baegyutae.portfolio.dto.PostCreateDto;

public interface PostService {

    Long createPost(PostCreateDto postCreateDto);
}

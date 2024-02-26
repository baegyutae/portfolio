package baegyutae.service;

import baegyutae.portfolio.dto.PostCreateDto;

public interface PostService {

    Long createPost(PostCreateDto postCreateDto);
}

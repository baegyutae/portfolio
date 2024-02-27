package baegyutae.portfolio.domain.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import baegyutae.portfolio.domain.model.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    void whenSavePost_thenSuccess() {
        // 게시글 생성 및 저장
        Post post = new Post("테스트 제목", "테스트 내용");
        Post savedPost = postRepository.save(post);

        // 저장된 게시글 검증
        assertNotNull(savedPost.getId());
        assertEquals("테스트 제목", savedPost.getTitle());
        assertEquals("테스트 내용", savedPost.getContent());
    }
}
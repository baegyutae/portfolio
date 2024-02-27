package baegyutae.portfolio.domain.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import baegyutae.portfolio.domain.model.Post;
import java.util.Optional;
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

    @Test
    void findPostByIdTest() {
        // 게시글 ID로 조회 테스트
        Post post = new Post("테스트 제목", "테스트 내용");
        Post savedPost = postRepository.save(post);

        Optional<Post> foundPost = postRepository.findById(savedPost.getId());

        assertThat(foundPost).isPresent();
        assertThat(foundPost.get().getTitle()).isEqualTo("테스트 제목");
    }

    @Test
    void updatePostTest() {
        // 게시글 수정 테스트
        Post post = Post.builder()
            .title("테스트 제목")
            .content("테스트 내용")
            .build();
        Post savedPost = postRepository.save(post);

        // 게시글 수정
        savedPost.update("수정된 제목", "수정된 내용");
        postRepository.save(savedPost);

        Post updatedPost = postRepository.findById(savedPost.getId()).orElseThrow();
        assertThat(updatedPost.getTitle()).isEqualTo("수정된 제목");
        assertThat(updatedPost.getContent()).isEqualTo("수정된 내용");
    }

    @Test
    void deletePostTest() {
        // 게시글 삭제 테스트
        Post post = new Post("테스트 제목", "테스트 내용");
        Post savedPost = postRepository.save(post);

        postRepository.delete(savedPost);

        Optional<Post> deletedPost = postRepository.findById(savedPost.getId());
        assertThat(deletedPost).isEmpty();
    }
}
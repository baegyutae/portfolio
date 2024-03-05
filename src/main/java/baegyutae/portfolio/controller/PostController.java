package baegyutae.portfolio.controller;

import baegyutae.portfolio.dto.PostCreateDto;
import baegyutae.portfolio.dto.PostResponseDto;
import baegyutae.portfolio.dto.PostUpdateDto;
import baegyutae.portfolio.service.PostService;
import baegyutae.portfolio.service.S3Service;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final S3Service s3Service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createPost(
        @RequestPart("postCreateDto") PostCreateDto postCreateDto,
        @RequestPart(value = "file", required = false) MultipartFile file) {

        // 파일이 제공되었는지 확인
        String fileUrl = null;
        if (file != null && !file.isEmpty()) {
            fileUrl = s3Service.uploadFile(file);
        }

        // 게시글과 파일 URL 저장 로직
        Long postId = postService.createPost(postCreateDto);
        return ResponseEntity.ok().body(Map.of("postId", postId, "fileUrl", fileUrl));
    }

    @GetMapping
    public List<PostResponseDto> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public PostResponseDto getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updatePost(@PathVariable Long id, @RequestBody PostUpdateDto postUpdateDto) {
        postService.updatePost(id, postUpdateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }

    @GetMapping("/generate-presigned-url")
    public ResponseEntity<String> generatePresignedUrl(@RequestParam String objectKey) {
        Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 60); // 예: 1시간 후 만료
        URL presignedUrl = s3Service.generatePresignedUrl(objectKey, expiration);
        return ResponseEntity.ok(presignedUrl.toString());
    }
}

package baegyutae.portfolio.controller;

import baegyutae.portfolio.dto.PostCreateDto;
import baegyutae.portfolio.dto.PostResponseDto;
import baegyutae.portfolio.dto.PostUpdateDto;
import baegyutae.portfolio.response.ApiResponse;
import baegyutae.portfolio.service.PostService;
import baegyutae.portfolio.service.S3Service;
import jakarta.validation.Valid;
import java.net.URL;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final S3Service s3Service;

    @PostMapping(consumes = {"multipart/form-data"})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<PostResponseDto>> createPost(
        @AuthenticationPrincipal UserDetails userDetails,
        @Valid @RequestPart("postCreateDto") PostCreateDto postCreateDto,
        @RequestPart(value = "file", required = false) MultipartFile file) {

        // 파일이 제공되었는지 확인
        String imageUrl = null;
        if (file != null && !file.isEmpty()) {
            imageUrl = s3Service.uploadFile(file);
        }

        PostCreateDto newPostCreateDto = new PostCreateDto(
            postCreateDto.title(),
            postCreateDto.content(),
            imageUrl
        );

        // 게시글과 파일 URL 저장 로직
        PostResponseDto postResponseDto = postService.createPost(newPostCreateDto);
        return ResponseEntity.ok(ApiResponse.success(postResponseDto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PostResponseDto>>> getAllPosts(
        @AuthenticationPrincipal UserDetails userDetails) {
        List<PostResponseDto> posts = postService.getAllPosts();
        return ResponseEntity.ok(ApiResponse.success(posts));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponseDto>> getPostById(
        @AuthenticationPrincipal UserDetails userDetails,
        @PathVariable Long id) {
        PostResponseDto post = postService.getPostById(id);
        return ResponseEntity.ok(ApiResponse.success(post));
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<ApiResponse<PostResponseDto>> updatePost(
        @PathVariable Long id,
        @Valid @RequestPart("postUpdateDto") PostUpdateDto postUpdateDto,
        @RequestPart(value = "file", required = false) MultipartFile file) {

        String imageUrl = null;
        if (file != null && !file.isEmpty()) {
            imageUrl = s3Service.uploadFile(file);
        }

        PostUpdateDto updatedPostUpdateDto = new PostUpdateDto(
            postUpdateDto.title(),
            postUpdateDto.content(),
            imageUrl != null ? imageUrl : postUpdateDto.imageUrl() // 새 이미지 URL 또는 기존 URL 사용
        );

        postService.updatePost(id, updatedPostUpdateDto);

        PostResponseDto updatedPost = postService.getPostById(id);
        return ResponseEntity.ok(ApiResponse.success(updatedPost));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<ApiResponse<Void>> deletePost(
        @AuthenticationPrincipal UserDetails userDetails,
        @PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @GetMapping("/generate-presigned-url")
    public ResponseEntity<String> generatePresignedUrl(@RequestParam String objectKey) {
        Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 60); // 예: 1시간 후 만료
        URL presignedUrl = s3Service.generatePresignedUrl(objectKey, expiration);
        return ResponseEntity.ok(presignedUrl.toString());
    }
}

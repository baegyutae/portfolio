package baegyutae.portfolio.controller;

import baegyutae.portfolio.entity.UploadedFile;
import baegyutae.portfolio.response.ApiError;
import baegyutae.portfolio.response.ApiResponse;
import baegyutae.portfolio.service.S3Service;
import baegyutae.portfolio.service.UploadedFileService;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileUploadController {

    private final S3Service s3Service;
    private final UploadedFileService uploadedFileService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<UploadedFile>> uploadFile(
        @RequestParam("file") MultipartFile file) {

        // 서버 사이드 파일 타입 검증
        String mimeType = file.getContentType();
        if (!mimeType.startsWith("image/")) {
            ApiError apiError = ApiError.builder()
                .message("허용되지 않는 파일 형식입니다.")
                .errors(List.of("허용되는 파일 형식: 이미지"))
                .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(apiError));
        }

        // 안전한 파일명 생성
        String safeFileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        try {
            URL presignedUrl = s3Service.generatePresignedUrl(safeFileName,
                new Date(System.currentTimeMillis() + 1000 * 60 * 60));
            UploadedFile uploadedFile = uploadedFileService.saveFileMetadata(safeFileName,
                presignedUrl.toString());

            return ResponseEntity.ok(ApiResponse.success(uploadedFile));
        } catch (AmazonS3Exception e) {
            ApiError apiError = ApiError.builder()
                .message("S3 서비스 엑세스 오류")
                .errors(List.of(e.getMessage()))
                .build();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(ApiResponse.error(apiError));
        } catch (Exception e) {
            ApiError apiError = ApiError.builder()
                .message("파일 업로드 중 예상치 못한 오류가 발생했습니다.")
                .errors(List.of(e.getMessage()))
                .build();
            return ResponseEntity.internalServerError().body(ApiResponse.error(apiError));
        }
    }
}

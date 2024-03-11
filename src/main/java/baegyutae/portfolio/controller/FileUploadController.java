package baegyutae.portfolio.controller;

import baegyutae.portfolio.entity.UploadedFile;
import baegyutae.portfolio.dto.FileUploadRequest;
import baegyutae.portfolio.service.S3Service;
import baegyutae.portfolio.service.UploadedFileService;
import java.net.URL;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileUploadController {

    private final S3Service s3Service;
    private final UploadedFileService uploadedFileService;

    @PostMapping("/upload")
    public ResponseEntity<UploadedFile> uploadFile(@RequestBody FileUploadRequest request) {
        // 파일을 업로드할 S3의 프리사인드 URL을 생성합니다.
        URL presignedUrl = s3Service.generatePresignedUrl(request.fileName(), new Date(System.currentTimeMillis() + 1000 * 60 * 60)); // 예: 1시간 후 만료

        // 업로드된 파일의 메타데이터를 저장합니다.
        UploadedFile uploadedFile = uploadedFileService.saveFileMetadata(request.fileName(), presignedUrl.toString());

        return ResponseEntity.ok(uploadedFile);
    }
}

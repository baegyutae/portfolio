package baegyutae.portfolio.service.impl;

import baegyutae.portfolio.entity.UploadedFile;
import baegyutae.portfolio.repository.UploadedFileRepository;
import baegyutae.portfolio.service.UploadedFileService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UploadedFileServiceImpl implements UploadedFileService {

    private final UploadedFileRepository uploadedFileRepository;

    @Override
    public UploadedFile saveFileMetadata(String fileName, String fileUrl) {
        UploadedFile uploadedFile = UploadedFile.builder()
            .fileName(fileName)
            .fileUrl(fileUrl)
            .uploadTime(LocalDateTime.now())
            .build();
        return uploadedFileRepository.save(uploadedFile);
    }
}

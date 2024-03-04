package baegyutae.portfolio.service;

import baegyutae.portfolio.domain.model.UploadedFile;

public interface UploadedFileService {
    UploadedFile saveFileMetadata(String fileName, String fileUrl);
}

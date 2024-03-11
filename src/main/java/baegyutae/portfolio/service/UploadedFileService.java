package baegyutae.portfolio.service;

import baegyutae.portfolio.entity.UploadedFile;

public interface UploadedFileService {
    UploadedFile saveFileMetadata(String fileName, String fileUrl);
}

package baegyutae.portfolio.repository;

import baegyutae.portfolio.entity.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadedFileRepository extends JpaRepository <UploadedFile, Long> {
}

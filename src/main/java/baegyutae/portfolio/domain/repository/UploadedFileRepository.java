package baegyutae.portfolio.domain.repository;

import baegyutae.portfolio.domain.model.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadedFileRepository extends JpaRepository <UploadedFile, Long> {
}

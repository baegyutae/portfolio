package baegyutae.portfolio.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadedFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String fileUrl;
    private LocalDateTime uploadTime;

    @Builder
    public UploadedFile(String fileName, String fileUrl, LocalDateTime uploadTime) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.uploadTime = uploadTime;
    }
}

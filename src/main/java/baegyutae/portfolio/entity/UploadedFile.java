package baegyutae.portfolio.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadedFile extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "파일 이름은 필수입니다.")
    @Size(max = 255, message = "파일 이름은 255자를 초과할 수 없습니다.")
    private String fileName;

    @NotBlank(message = "파일 URL은 필수입니다.")
    @URL(message = "유효한 URL 형식이어야 합니다.")
    private String fileUrl;

}

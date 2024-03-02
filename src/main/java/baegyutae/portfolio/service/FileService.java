package baegyutae.portfolio.service;

import java.net.URL;
import java.util.Date;

public interface FileService {

    URL generatePresignedUrl(String bucketName, String objectKey, Date expiration);
}

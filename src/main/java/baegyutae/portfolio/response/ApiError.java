package baegyutae.portfolio.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiError {
    private final boolean success = false;
    private final String message;
    private final List<String> errors;
}

package baegyutae.portfolio.exception;

public class UsernameOrEmailAlreadyInUseException extends RuntimeException {
    public UsernameOrEmailAlreadyInUseException(String message) {
        super(message);
    }
}

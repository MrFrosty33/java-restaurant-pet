package at.frosty.restaurant.common.exception;

public class ConflictException extends CustomException {
    public ConflictException(String message, ErrorType errorType) {
        super(message, errorType);
    }
}

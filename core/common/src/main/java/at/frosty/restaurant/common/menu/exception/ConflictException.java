package at.frosty.restaurant.common.menu.exception;

public class ConflictException extends CustomException {
    public ConflictException(String message, ErrorType errorType) {
        super(message, errorType);
    }
}

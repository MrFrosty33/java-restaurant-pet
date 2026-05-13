package at.frosty.restaurant.common.exception;

public class NotFoundException extends CustomException {
    public NotFoundException(String message, ErrorType errorType) {
        super(message, errorType);
    }
}

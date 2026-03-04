package at.frosty.restaurant.common.menu.exception;

public class NotFoundException extends CustomException {
    public NotFoundException(String message, ErrorType errorType) {
        super(message, errorType);
    }
}

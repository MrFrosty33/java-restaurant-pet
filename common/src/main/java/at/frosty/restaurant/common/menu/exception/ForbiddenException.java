package at.frosty.restaurant.common.menu.exception;

public class ForbiddenException extends CustomException {
    public ForbiddenException(String message, ErrorType errorType) {
        super(message, errorType);
    }
}

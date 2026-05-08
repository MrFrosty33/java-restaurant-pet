package at.frosty.restaurant.common.menu.exception;

public class InternalServerException extends CustomException {
    public InternalServerException(String message, ErrorType errorType) {
        super(message, errorType);
    }
}

package at.frosty.restaurant.common.exception;

public class InternalServerException extends CustomException {
    public InternalServerException(String message, ErrorType errorType) {
        super(message, errorType);
    }
}

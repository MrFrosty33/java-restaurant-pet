package at.frosty.restaurant.common.exception;

public class ServiceUnavailableException extends CustomException {
    public ServiceUnavailableException(String message, ErrorType errorType) {
        super(message, errorType);
    }
}

package at.frosty.restaurant.common.exception;

public class CustomException extends RuntimeException implements IncludesErrorType {
    private final ErrorType errorType;
    public CustomException(String message, ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }

    @Override
    public ErrorType getErrorType() {
        return errorType;
    }
}

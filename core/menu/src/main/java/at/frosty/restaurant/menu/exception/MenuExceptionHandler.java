package at.frosty.restaurant.menu.exception;

import at.frosty.restaurant.common.menu.exception.ErrorMessage;
import at.frosty.restaurant.common.menu.exception.ErrorType;
import at.frosty.restaurant.common.menu.exception.IncludesErrorType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class MenuExceptionHandler {
    private final String className = this.getClass().getSimpleName();

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleOthers(Exception e, HttpServletRequest request) {
        writeLog(e);
        return ErrorMessage.builder()
                .message(e.getMessage())
                .errorType(ErrorType.INTERNAL_ERROR)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .apiPath(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
    }

    private void writeLog(Exception ex) {
        ErrorType errorType;
        if(ex instanceof IncludesErrorType) errorType = ((IncludesErrorType) ex).getErrorType();
        else errorType = ErrorType.INTERNAL_ERROR;

        log.warn("{}: caught {} with errorType={}. message={}", className,
                ex.getClass().getSimpleName(),
                errorType,
                ex.getMessage());
    }
}

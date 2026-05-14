package at.frosty.restaurant.table.exception;

import at.frosty.restaurant.common.exception.ConflictException;
import at.frosty.restaurant.common.exception.ErrorMessage;
import at.frosty.restaurant.common.exception.ErrorType;
import at.frosty.restaurant.common.exception.ForbiddenException;
import at.frosty.restaurant.common.exception.IncludesErrorType;
import at.frosty.restaurant.common.exception.NotFoundException;
import at.frosty.restaurant.common.exception.ServiceUnavailableException;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class TableExceptionHandler {
    private final String className = this.getClass().getSimpleName();

    // custom Exceptions

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessage handleConflict(ConflictException e, HttpServletRequest request) {
        writeLog(e);
        return ErrorMessage.builder()
                .message(e.getMessage())
                .errorType(e.getErrorType())
                .status(HttpStatus.CONFLICT)
                .apiPath(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleNotFound(NotFoundException e, HttpServletRequest request) {
        writeLog(e);
        return ErrorMessage.builder()
                .message(e.getMessage())
                .errorType(e.getErrorType())
                .status(HttpStatus.NOT_FOUND)
                .apiPath(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessage handleForbidden(ForbiddenException e, HttpServletRequest request) {
        writeLog(e);
        return ErrorMessage.builder()
                .message(e.getMessage())
                .errorType(e.getErrorType())
                .status(HttpStatus.FORBIDDEN)
                .apiPath(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
    }

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

    // spring Exceptions

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleMissingServletRequestParameter(MissingServletRequestParameterException e, HttpServletRequest request) {
        writeLog(e);
        return ErrorMessage.builder()
                .message(e.getMessage())
                .errorType(ErrorType.VALIDATION_ERROR)
                .status(HttpStatus.BAD_REQUEST)
                .apiPath(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {
        writeLog(e);
        return ErrorMessage.builder()
                .message(e.getMessage())
                .errorType(ErrorType.VALIDATION_ERROR)
                .status(HttpStatus.BAD_REQUEST)
                .apiPath(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
    }

    // jakarta Exceptions

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleConstraintViolation(ConstraintViolationException e, HttpServletRequest request) {
        writeLog(e);
        return ErrorMessage.builder()
                .message(e.getMessage())
                .errorType(ErrorType.VALIDATION_ERROR)
                .status(HttpStatus.BAD_REQUEST)
                .apiPath(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
    }


    private void writeLog(Exception ex) {
        ErrorType errorType;
        if (ex instanceof IncludesErrorType) errorType = ((IncludesErrorType) ex).getErrorType();
        else if (ex instanceof MissingServletRequestParameterException) errorType = ErrorType.VALIDATION_ERROR;
        else if (ex instanceof ConstraintViolationException) errorType = ErrorType.VALIDATION_ERROR;
        else if (ex instanceof MethodArgumentNotValidException) errorType = ErrorType.VALIDATION_ERROR;
        else errorType = ErrorType.INTERNAL_ERROR;

        log.warn("{}: caught {} with errorType={}. message={}", className,
                ex.getClass().getSimpleName(),
                errorType,
                ex.getMessage());
    }
}

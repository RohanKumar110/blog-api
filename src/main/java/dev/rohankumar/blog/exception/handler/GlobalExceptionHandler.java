package dev.rohankumar.blog.exception.handler;

import dev.rohankumar.blog.exception.*;
import dev.rohankumar.blog.payload.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        ErrorResponse error = createErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCategoryNotFoundException(CategoryNotFoundException ex){
        ErrorResponse error = createErrorResponse(HttpStatus.NOT_FOUND,ex.getMessage());
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePostNotFoundException(PostNotFoundException ex){
        ErrorResponse error = createErrorResponse(HttpStatus.NOT_FOUND,ex.getMessage());
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleImageNotFoundException(ImageNotFoundException ex){
        ErrorResponse error = createErrorResponse(HttpStatus.NOT_FOUND,ex.getMessage());
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotAnImageFileException.class)
    public ResponseEntity<ErrorResponse> handleNotAnImageFileException(NotAnImageFileException ex){
        ErrorResponse error = createErrorResponse(HttpStatus.BAD_REQUEST,ex.getMessage());
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerErrorException(MethodArgumentNotValidException ex) {
        ErrorResponse error = createErrorResponse(HttpStatus.BAD_REQUEST, "Invalid Inputs");
        error.setErrors(new HashMap<>());
        ex.getBindingResult().getAllErrors().forEach(e -> {
            String field = ((FieldError)e).getField();
            String message = e.getDefaultMessage();
            error.getErrors().put(field,message);
        });
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInternalServerErrorException() {
        ErrorResponse error = createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while processing the request");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorResponse createErrorResponse(HttpStatus httpStatus, String message) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setHttpStatusCode(httpStatus.value());
        errorResponse.setHttpStatus(httpStatus);
        errorResponse.setReason(httpStatus.getReasonPhrase().toUpperCase());
        errorResponse.setMessage(message.toUpperCase());
        return errorResponse;
    }
}

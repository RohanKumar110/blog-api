package dev.rohankumar.blog.exception;

public class NotAnImageFileException extends RuntimeException{

    public NotAnImageFileException(String message) {
        super(message);
    }
}

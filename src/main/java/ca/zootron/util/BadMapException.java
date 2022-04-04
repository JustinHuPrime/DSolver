package ca.zootron.util;

public class BadMapException extends Exception {

    public BadMapException(String message) {
        super(message);
    }

    public BadMapException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadMapException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

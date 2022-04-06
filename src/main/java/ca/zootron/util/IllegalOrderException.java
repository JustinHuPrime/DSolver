package ca.zootron.util;

public class IllegalOrderException extends RuntimeException {

    public IllegalOrderException(String message) {
        super(message);
    }

    public IllegalOrderException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalOrderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

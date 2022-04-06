package ca.zootron.util;

public class NoSuchMapException extends RuntimeException {

    public NoSuchMapException(String message) {
        super(message);
    }

    public NoSuchMapException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchMapException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

package ca.zootron.util;

import ca.zootron.model.order.Order;
import org.jetbrains.annotations.Nullable;

public class IllegalOrderListException extends RuntimeException {

    @Nullable
    public final Order violator;

    public IllegalOrderListException(@Nullable Order violator, String message) {
        super(message);
        this.violator = violator;
    }

    public IllegalOrderListException(@Nullable Order violator, String message, Throwable cause) {
        super(message, cause);
        this.violator = violator;
    }

    public IllegalOrderListException(@Nullable Order violator, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.violator = violator;
    }
}

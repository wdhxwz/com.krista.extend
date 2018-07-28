package com.krista.extend.mvc.limit;

public class LimitException extends RuntimeException{
    public LimitException(String message) {
        super(message);
    }

    public LimitException(String message, Throwable cause) {
        super(message, cause);
    }
}
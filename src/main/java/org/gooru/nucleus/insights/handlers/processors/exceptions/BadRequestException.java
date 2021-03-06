package org.gooru.nucleus.insights.handlers.processors.exceptions;

public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = -8724444379963076817L;

    public BadRequestException() {
    }

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(Throwable cause) {
        super(cause);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

}

package io.tech1.acceptance.exception;

public class AcceptanceException extends Exception {
    private static final long serialVersionUID = 1L;

    public AcceptanceException(String errorMessage) {
        super(errorMessage);
    }
}
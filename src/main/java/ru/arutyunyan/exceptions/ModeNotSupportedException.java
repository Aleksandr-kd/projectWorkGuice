package ru.arutyunyan.exceptions;

public class ModeNotSupportedException extends RuntimeException {

    public ModeNotSupportedException(String modeName) {
        super(String.format("Browser mode '%s' is not supported", modeName));
    }
}

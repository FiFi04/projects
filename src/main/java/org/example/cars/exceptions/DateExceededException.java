package org.example.cars.exceptions;

public class DateExceededException extends RuntimeException {
    public DateExceededException(String message) {
        super(message);
    }
}

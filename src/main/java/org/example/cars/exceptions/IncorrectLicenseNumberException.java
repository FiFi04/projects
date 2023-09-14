package org.example.cars.exceptions;

public class IncorrectLicenseNumberException extends RuntimeException {
    public IncorrectLicenseNumberException(String message) {
        super(message);
    }
}

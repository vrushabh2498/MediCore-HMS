package com.medicore.patient.exception;

public class PatientAlreadyExitsException extends RuntimeException {
    public PatientAlreadyExitsException(String message) {
        super(message);
    }
}

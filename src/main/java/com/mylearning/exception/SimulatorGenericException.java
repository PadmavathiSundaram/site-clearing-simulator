package com.mylearning.exception;

/**
 * SimulatorGenericException Exception
 */
public class SimulatorGenericException extends Exception {

    private String message;
    private String errorCode;

    public SimulatorGenericException(String errorCode, String message) {
        this.message = message;
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getFormattedMessage() {
        return message.concat(" : ").concat(errorCode);
    }

    public String getErrorCode() {return errorCode;}
}

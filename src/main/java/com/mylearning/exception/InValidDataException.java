package com.mylearning.exception;

/**
 * InValidData Exception
 */
public class InValidDataException extends SimulatorGenericException {

    public InValidDataException(String errorCode, String message) {
        super(errorCode, message);
    }

}

package com.mylearning.exception;

/**
 * FatalException Exception - for Terminal Conditions during Simulation
 */
public class FatalException extends SimulatorGenericException {

    public FatalException(String errorCode, String message) {
        super(errorCode, message);
    }

}

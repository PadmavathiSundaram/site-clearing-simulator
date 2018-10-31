package com.mylearning.controller;

import com.mylearning.delegate.MasterDelegate;

import java.util.logging.Logger;

/**
 * Handles uncaught Exceptions if any
 */
public class GlobalExceptionController implements Thread.UncaughtExceptionHandler {

    private static Logger logger = Logger.getLogger(GlobalExceptionController.class.getName());

    private MasterDelegate masterDelegate;

    public GlobalExceptionController(){
        this.masterDelegate = new MasterDelegate();
    }

    public GlobalExceptionController(MasterDelegate masterDelegate) {
        this.masterDelegate = masterDelegate;
    }

    /**
     * Unhandled exception caught and handled
     * @param t
     * @param e
     */
    public void uncaughtException(Thread t, Throwable e) {
        logger.info("Unhandled exception caught!");
        handleException(e);
    }

    private void handleException(Throwable e) {
        masterDelegate.shutDownSimulatorOnError(e.getMessage());
    }

}

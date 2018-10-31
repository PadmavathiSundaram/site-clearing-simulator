package com.mylearning.delegate;

import com.mylearning.exception.InValidDataException;
import com.mylearning.exception.SimulatorGenericException;

import java.util.Scanner;

/**
 * A single point of contact exposed to outside world to manage all delegations
 */
public class MasterDelegate {

    private SetupDelegate setupDelegate;
    private OperationDelegate operationDelegate;
    private ShutDownDelegate shutDownDelegate;

    public MasterDelegate(){
        this.setupDelegate = new SetupDelegate();
        this.operationDelegate = new OperationDelegate();
        this.shutDownDelegate = new ShutDownDelegate();
    }

    /**
     *
     * @param shutDownDelegate
     */
    public void setShutDownDelegate(ShutDownDelegate shutDownDelegate) {
        this.shutDownDelegate = shutDownDelegate;
    }

    /**
     *
     * @param message
     */
    public void shutDownSimulatorOnError(String message) {
        shutDownDelegate.shutDownSimulatorOnError(message);
    }

    /**
     *
     * @param terminate
     */
    public void shutDownSimulator(boolean terminate) {
        if (terminate) {
            shutDownDelegate.shutDownSimulator();
        }
    }

    /**
     *
     * @param in
     * @return
     * @throws SimulatorGenericException
     */
    public boolean executeOperation(Scanner in) throws SimulatorGenericException {
        boolean terminate = operationDelegate.executeOperation(in);
        shutDownSimulator(terminate);
        return terminate;
    }

    /**
     *
     * @param args
     * @throws InValidDataException
     */
    public void setupSimulator(String[] args) throws InValidDataException {
        setupDelegate.setupSimulator(args);
    }
}

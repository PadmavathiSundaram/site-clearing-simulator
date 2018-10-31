package com.mylearning.controller;

import com.mylearning.constants.SimulatorConstants;
import com.mylearning.delegate.MasterDelegate;
import com.mylearning.exception.InValidDataException;
import com.mylearning.exception.SimulatorGenericException;
import com.mylearning.utils.StaticDataLoaderUtil;

import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controls the Sequence of events.
 * Responsible for Initiating -> Simulator Setup, Operation Execution and ShutDown of Simulator
 */
public class SimulatorController {

    public static final Logger logger = Logger.getLogger(SimulatorController.class.getName());

    private MasterDelegate masterDelegate;

    public SimulatorController() {
        this.masterDelegate = new MasterDelegate();
    }

    SimulatorController(MasterDelegate masterDelegate) {
        this.masterDelegate = masterDelegate;
    }

    /**
     * Starting point for simulator.Loads the setup and handles user Operation inputs
     * @param args
     * @param operations
     */
    public void runSimulation(String[] args, InputStream operations) {
        try {
            masterDelegate.setupSimulator(args);
            serviceOperations(new Scanner(operations));
        } catch (InValidDataException e) {
            logger.log(Level.SEVERE, e.getFormattedMessage());
        } catch (Exception e) {
            String message = StaticDataLoaderUtil.getConfigValueAsString(SimulatorConstants.ErrorCode_E1009)
                    .concat(e.getMessage())
                    .concat(" : ")
                    .concat(SimulatorConstants.ErrorCode_E1009);
            logger.log(Level.SEVERE, message);
        } finally {
            return;
        }
    }

    /**
     * Handles Input - one line at a time
     * @param inLine
     */
    public void serviceOperations(Scanner inLine) {

        Scanner in = null;
        boolean terminate = false;
        try {
            while (!terminate && inLine.hasNextLine()) {
                in = new Scanner(inLine.nextLine());
                terminate = serviceCurrentInputLine(in);
            }
        } catch (SimulatorGenericException e) {
            handleException(e);
        } finally {
            if (in != null) {
                in.close();
            }
            inLine.close();
        }
        return;
    }

    /**
     * Accepts and processes data in a single input line
     * Delegates OperationExecution
     * @param in
     * @throws SimulatorGenericException
     */
    private boolean serviceCurrentInputLine(Scanner in) throws SimulatorGenericException {

        boolean terminate = false;
        while (!terminate && in.hasNext()) {
            terminate = masterDelegate.executeOperation(in);
        }
        in.close();
        return terminate;
    }


    /**
     * Handles exception and Data validations errors
     * @param e
     */
    private void handleException(SimulatorGenericException e) {
        masterDelegate.shutDownSimulatorOnError(e.getMessage());
        return;

    }


}

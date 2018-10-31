package com.mylearning;

import com.mylearning.controller.SimulatorController;


/**
 * SiteClearingSimulator
 */
public class SiteClearingSimulator {

    /**
     * Main method
     *
     * @param args
     */
    public static void main(String args[]) {
        SimulatorController controller = new SimulatorController();
        controller.runSimulation(args, System.in);
    }
}

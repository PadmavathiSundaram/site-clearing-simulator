package com.mylearning.delegate;

import com.mylearning.constants.SimulatorConstants;
import com.mylearning.model.Simulator;
import com.mylearning.service.CostService;
import com.mylearning.service.impl.CostServiceImpl;
import com.mylearning.utils.StaticDataLoaderUtil;

import java.util.Optional;

/**
 * Handles how the simulator Terminates
 */
public class ShutDownDelegate {
    private CostService costService;

    public ShutDownDelegate(){
        this.costService = new CostServiceImpl();
    }

    public ShutDownDelegate(CostService costService) {
        this.costService = costService;
    }

    /**
     * displays operation history and cost before Termination
     */
    public void shutDownSimulator() {
        displayOperationHistory();
        this.costService.displayCost();
    }

    /**
     * Handles Termination caused due to Exceptions
     * @param message
     */
    public void shutDownSimulatorOnError(final String message) {

        switch (message) {
            case SimulatorConstants.PROTECTED_TREE_DESTRUCTION_TEXT:
            case SimulatorConstants.NAVIGATION_BEYOND_SITE:
                StaticDataLoaderUtil.populateStaticText(message);
                break;
            default:
                System.out.println(SimulatorConstants.NEW_LINE + message);
                StaticDataLoaderUtil.populateStaticText(SimulatorConstants.ERROR_TEXT);
                break;
        }
        shutDownSimulator();
    }

    private void displayOperationHistory() {
        Optional<String> operations = Simulator.operationHistory.stream().reduce((i, j) -> String.join(" , ", i, j));
        if (operations.isPresent()) {
            System.out.println(operations.get());
        }
    }

}

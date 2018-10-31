package com.mylearning.delegate;

import com.mylearning.constants.Operations;
import com.mylearning.constants.SimulatorConstants;
import com.mylearning.exception.InValidDataException;
import com.mylearning.exception.SimulatorGenericException;
import com.mylearning.service.OperationService;
import com.mylearning.service.impl.OperationServiceImpl;
import com.mylearning.strategy.NavigationStrategy;
import com.mylearning.strategy.impl.AdvanceNavigationStrategyImpl;
import com.mylearning.utils.StaticDataLoaderUtil;

import java.util.Scanner;

/**
 * Validates the Operation input and uses the Operation Service to execute the operation.
 */
public class OperationDelegate {

    private OperationService operationService;

    public OperationDelegate() {
        this.operationService = new OperationServiceImpl();
    }

    /**
     * Process user input and if valid executes the operation
     * @param input
     * @return
     * @throws SimulatorGenericException
     */
    public boolean executeOperation(Scanner input) throws SimulatorGenericException {

        Operations operations = fetchOperation(input.next());

        NavigationStrategy navigationStrategy = operations.getNavigationStrategy();

        if (operations == Operations.A) {
            int squaresToDestination = fetchAdvanceCount(input);
            navigationStrategy = new AdvanceNavigationStrategyImpl(squaresToDestination);
        }

        return operationService.operate(navigationStrategy);
    }

    private Operations fetchOperation(String input) throws InValidDataException {

        Operations operations = Operations.getIfPresent(input);
        if (operations == null) {
            String message = StaticDataLoaderUtil.getConfigValueAsString(SimulatorConstants.ErrorCode_E1004);
            throw new InValidDataException(SimulatorConstants.ErrorCode_E1004, message);
        }

        return operations;
    }

    private int fetchAdvanceCount(Scanner in) throws InValidDataException {
        if (!in.hasNextInt()) {
            String message = StaticDataLoaderUtil.getConfigValueAsString(SimulatorConstants.ErrorCode_E1005);
            throw new InValidDataException(SimulatorConstants.ErrorCode_E1005, message);
        }
        return in.nextInt();
    }
}

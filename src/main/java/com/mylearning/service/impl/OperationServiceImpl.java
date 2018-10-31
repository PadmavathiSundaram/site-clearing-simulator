package com.mylearning.service.impl;

import com.mylearning.exception.FatalException;
import com.mylearning.service.OperationService;
import com.mylearning.strategy.NavigationStrategy;

public class OperationServiceImpl implements OperationService {

    /**
     * Navigates based on Navigation Strategy
     * @param navigationStrategy
     * @return
     * @throws FatalException
     */
    @Override
    public boolean operate(NavigationStrategy navigationStrategy) throws FatalException {
        navigationStrategy.updateOperationHistory();
        return navigationStrategy.navigate();
    }
}

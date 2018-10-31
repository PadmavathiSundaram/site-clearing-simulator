package com.mylearning.strategy;


import com.mylearning.exception.FatalException;
import com.mylearning.service.CostService;
import com.mylearning.service.impl.CostServiceImpl;

public interface NavigationStrategy {
    CostService COST_SERVICE = new CostServiceImpl();
    boolean navigate() throws FatalException;
    void updateOperationHistory();
}

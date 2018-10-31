package com.mylearning.strategy.impl;

import com.mylearning.constants.Operations;
import com.mylearning.constants.SimulatorConstants;
import com.mylearning.model.Simulator;
import com.mylearning.strategy.NavigationStrategy;
import com.mylearning.utils.StaticDataLoaderUtil;


public class QuitStrategyImpl implements NavigationStrategy {
    /**
     * Terminates the simulation
     * @return
     */
    @Override
    public boolean navigate() {
        StaticDataLoaderUtil.populateStaticText(SimulatorConstants.QUIT_OPERATION_TEXT);
        return true;
    }

        @Override
        public void updateOperationHistory () {
            Simulator.operationHistory.add(Operations.Q.getOperation());
        }
    }

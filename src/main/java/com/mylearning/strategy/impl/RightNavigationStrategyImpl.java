package com.mylearning.strategy.impl;

import com.mylearning.constants.Operations;
import com.mylearning.constants.Orientation;
import com.mylearning.constants.SimulatorConstants;
import com.mylearning.model.Simulator;
import com.mylearning.strategy.NavigationStrategy;
import com.mylearning.utils.StaticDataLoaderUtil;


public class RightNavigationStrategyImpl implements NavigationStrategy {

    /**
     * Alters the orientation of the current square
     * @return
     */
    @Override
    public boolean navigate() {
        COST_SERVICE.incrementCommunicationOverhead();
        int currentOrientationIndex = Simulator.currentSquare.getFacingTo().getOrientationIndex();
        int newOrientationIndex = ++currentOrientationIndex;
        newOrientationIndex = Orientation.orientationIndex.size() == newOrientationIndex ? 0 : newOrientationIndex;
        Orientation nextOrientation = Orientation.orientationIndex.get(newOrientationIndex);

        Simulator.currentSquare.setFacingTo(nextOrientation);
        StaticDataLoaderUtil.populateStaticTextinLine(SimulatorConstants.AVAILABLE_OPERATIONS_TEXT);
        return false;
    }

    @Override
    public void updateOperationHistory() {
        Simulator.operationHistory.add(SimulatorConstants.TURN + Operations.R.getOperation());
    }
}

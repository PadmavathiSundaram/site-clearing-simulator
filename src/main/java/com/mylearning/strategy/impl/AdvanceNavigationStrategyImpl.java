package com.mylearning.strategy.impl;

import com.mylearning.constants.Operations;
import com.mylearning.constants.SimulatorConstants;
import com.mylearning.exception.FatalException;
import com.mylearning.model.Coordinate;
import com.mylearning.model.Simulator;
import com.mylearning.model.Square;
import com.mylearning.strategy.NavigationStrategy;
import com.mylearning.utils.StaticDataLoaderUtil;

/**
 * Handles Advance Operation
 */
public class AdvanceNavigationStrategyImpl implements NavigationStrategy {

    int squaresToDestination = 1;

    public AdvanceNavigationStrategyImpl(int squaresToDestination) {
        this.squaresToDestination = squaresToDestination;
    }

    public AdvanceNavigationStrategyImpl() {
        this.squaresToDestination = 1;
    }

    /**
     * Moves by the specified number of squares in the current direction
     * Fata exception thrown if user attempts to move beyond the site map
     * @return
     * @throws FatalException
     */
    @Override
    public boolean navigate() throws FatalException {
        COST_SERVICE.incrementCommunicationOverhead();
        for (int i = 0; i < squaresToDestination; i++) {
            navigateOneSquare();
            COST_SERVICE.updateCost(i == squaresToDestination - 1);
        }
        StaticDataLoaderUtil.populateStaticTextinLine(SimulatorConstants.AVAILABLE_OPERATIONS_TEXT);
        return false;
    }

    private void navigateOneSquare() throws FatalException {
        Square source = Simulator.currentSquare;
        if (source == null) {
            //Initializing
            Simulator.currentSquare = Simulator.squares[0][0];
            return;
        }

        Coordinate nextCoordinate = Simulator.currentSquare.getFacingTo().getNextCoordinates(source.getCoordinate());

        if (Simulator.squares.length > nextCoordinate.getX() && Simulator.squares[0].length > nextCoordinate.getY()) {
            Square nextSquare = Simulator.squares[nextCoordinate.getX()][nextCoordinate.getY()];
            nextSquare.setFacingTo(source.getFacingTo());
            Simulator.currentSquare = nextSquare;
        } else {
            String message = StaticDataLoaderUtil.getConfigValueAsString(SimulatorConstants.ErrorCode_E1008);
            throw new FatalException(SimulatorConstants.ErrorCode_E1008,message);
        }
    }

    @Override
    public void updateOperationHistory() {
        Simulator.operationHistory.add(Operations.A.getOperation() + " " + squaresToDestination);
    }



}

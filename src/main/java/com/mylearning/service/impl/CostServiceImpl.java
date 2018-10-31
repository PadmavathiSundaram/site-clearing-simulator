package com.mylearning.service.impl;

import com.mylearning.constants.CostItems;
import com.mylearning.constants.Land;

import com.mylearning.constants.SimulatorConstants;
import com.mylearning.exception.FatalException;
import com.mylearning.model.Simulator;
import com.mylearning.service.CostService;
import com.mylearning.utils.StaticDataLoaderUtil;

import java.util.Arrays;

public class CostServiceImpl implements CostService {

    /**
     * Already cleared lands are treated like plain lands
     * @param isDestination
     * @throws FatalException
     */
    @Override
    public void updateCost(boolean isDestination) throws FatalException {
        if(Simulator.currentSquare.getIsCleared()) {
            Land.o.updateCostParameters(isDestination);
        } else {
            Simulator.currentSquare.getLand().updateCostParameters(isDestination);
            clearLand();
        }
    }

    /**
     * Tracks operation overhead
     */
    @Override
    public void incrementCommunicationOverhead() {
        CostItems.communicationOverheadLabel.incrementQuantity(1);
    }

    /**
     * Displays the cost report table based on the cost Items
     * @return
     */
    @Override
    public int displayCost() {
        StaticDataLoaderUtil.populateStaticText(SimulatorConstants.COST_REPORT_TEXT);

        System.out.format(SimulatorConstants.COST_REPORT_HEADER_FORMAT,"Item","Quality","Cost");
        System.out.println();

        final int[] totalCost = {0};
        Arrays.stream(CostItems.values()).forEach(costItem -> {
            totalCost[0] = totalCost[0] + updateTotal(costItem.getLabel(),
                    costItem.getQuantityForCostItem(),
                    costItem.getCreditForCostItem());
        });

        System.out.println("----");
        System.out.format("%s %55s", "Total", totalCost[0]);
        System.out.println();

        StaticDataLoaderUtil.populateStaticText(SimulatorConstants.THANK_YOU_TEXT);
        return totalCost[0];
    }


    private int updateTotal(String label, int count, int credits){
        int cost = count * credits;
        StaticDataLoaderUtil.displayCostParameters(label, count, cost);
        return cost;
    }

    private void clearLand() {
        CostItems.unClearedSquaresLabel.incrementQuantity(-1);
        Simulator.currentSquare.setIsCleared(true);
    }

}

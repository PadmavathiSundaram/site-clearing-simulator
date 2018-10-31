package com.mylearning.constants;

import com.mylearning.exception.FatalException;
import com.mylearning.utils.StaticDataLoaderUtil;

/**
 * Different types of landscape in the site Map
 */
public enum Land {
    o("o") {
        @Override
        public void updateCostParameters(boolean isDestination) {
            CostItems.fuelUsageLabel.incrementQuantity(1);
        }
    },
    r("r") {
        @Override
        public void updateCostParameters(boolean isDestination) {
            CostItems.fuelUsageLabel.incrementQuantity(2);
        }
    },
    t("t") {
        @Override
        public void updateCostParameters(boolean isDestination) {
            CostItems.fuelUsageLabel.incrementQuantity(2);
            if(!isDestination){
                CostItems.paintDamageLabel.incrementQuantity(1);
            }
        }
    },
    T("T") {
        @Override
        public void updateCostParameters(boolean isDestination) throws FatalException {
            CostItems.fuelUsageLabel.incrementQuantity(2);
            CostItems.destructionProtectedTreeLabel.incrementQuantity(1);
            String message = StaticDataLoaderUtil.getConfigValueAsString(SimulatorConstants.ErrorCode_E1001);
            throw new FatalException(SimulatorConstants.ErrorCode_E1001, message);
        }
    };

    private final String land;

    Land(String land) {
        this.land = land;
    }

    public void updateCostParameters(boolean isDestination) throws FatalException {
    }

}

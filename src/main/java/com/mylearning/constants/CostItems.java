package com.mylearning.constants;

import com.mylearning.utils.StaticDataLoaderUtil;

/**
 * Items that make the final cost Report table
 */
public enum CostItems {
    communicationOverheadLabel(SimulatorConstants.COMMUNICATION_OVERHEAD_LABEL) {
        @Override
        public int getCreditForCostItem() {
            return getCreditAsInt(SimulatorConstants.COMMUNICATION_OVERHEAD_CREDIT);
        }

    },
    fuelUsageLabel(SimulatorConstants.FUEL_USAGE_LABEL) {
        @Override
        public int getCreditForCostItem() {
            return getCreditAsInt(SimulatorConstants.FUEL_CREDIT);
        }

    },
    unClearedSquaresLabel(SimulatorConstants.UN_CLEARED_SQUARES_LABEL) {
        @Override
        public int getCreditForCostItem() {
            return getCreditAsInt(SimulatorConstants.UNCLEARED_SQUARE_CREDIT);
        }

    },
    destructionProtectedTreeLabel(SimulatorConstants.DESTRUCTION_PROTECTED_TREE_LABEL) {
        @Override
        public int getCreditForCostItem() {
            return getCreditAsInt(SimulatorConstants.PROTECTED_TREEDESTRUCTION_CREDIT);
        }

    },
    paintDamageLabel(SimulatorConstants.PAINT_DAMAGE_LABEL) {
        @Override
        public int getCreditForCostItem() {
            return getCreditAsInt(SimulatorConstants.PAINT_DAMAGE_CREDIT);
        }

    };

    private String label;

    private int quantity;

    CostItems(String label) {
        this.label = label;
        this.quantity = 0;
    }

    public String getLabel() {
        return this.label;
    }

    public int getCreditForCostItem() {
        return 0;
    }

    public int getQuantityForCostItem() {
        return this.quantity;
    }

    public int getCreditAsInt(String key) {
        return StaticDataLoaderUtil.getConfigValueAsInt(key);
    }

    public void incrementQuantity(int i) {
        quantity = quantity + i;
    }

}

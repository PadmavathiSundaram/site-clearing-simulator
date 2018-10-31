package com.mylearning.utils;

import com.mylearning.constants.SimulatorConstants;


public class StaticDataLoaderUtil {

    private StaticDataLoaderUtil(){}

    /**
     * Populates the configuration values (at the start of the APP)
     */
    public static void populateStaticText(String textKey) {
        System.out.println(SimulatorConstants.NEW_LINE
                + SimulatorConstants.TEXT.getProperty(textKey)
                + SimulatorConstants.NEW_LINE);
    }

    public static void populateStaticTextinLine(String textKey) {
        System.out.print(SimulatorConstants.TEXT.getProperty(textKey));
    }

    /**
     * Reads config value for the specified property name
     *
     * @param property
     * @return
     */
    public static String getTextForLabel(String property) {
        return SimulatorConstants.TEXT.getProperty(property);
    }

    /**
     * Displays the cost items
     * @param label
     * @param count
     * @param cost
     */
    public static void displayCostParameters(String label, int count, int cost){
        System.out.format(SimulatorConstants.COST_REPORT_HEADER_FORMAT, getTextForLabel(label), count, cost );
        System.out.println();
    }

    /**
     * Reads config values as int
     *
     * @param property
     * @return
     */
    public static int getConfigValueAsInt(String property) {
        return Integer.parseInt(getConfigValueAsString(property));
    }

    /**
     * Reads config value for the specified property name
     *
     * @param property
     * @return
     */
    public static String getConfigValueAsString(String property) {
        return SimulatorConstants.PROPERTIES.getProperty(property);
    }

}

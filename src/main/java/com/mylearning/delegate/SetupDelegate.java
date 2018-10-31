package com.mylearning.delegate;

import com.mylearning.constants.SimulatorConstants;
import com.mylearning.controller.GlobalExceptionController;
import com.mylearning.exception.InValidDataException;

import com.mylearning.service.GridSetupService;
import com.mylearning.service.impl.GridSetupServiceImpl;
import com.mylearning.utils.FileLoaderUtil;
import com.mylearning.utils.StaticDataLoaderUtil;

/**
 * Governs the Sequence of items involved in loading the simulator
 */
public class SetupDelegate {

    private GridSetupService gridSetupService;

    /**
     * Loads and process the file to generate the site map
     * Loads config and static content
     * @param filename
     * @throws InValidDataException
     */
    public void setupSimulator(String[] filename) throws InValidDataException {
        setUpExceptionController();
        FileLoaderUtil.populateProperties(SimulatorConstants.CONTENT_FILE_PATH, SimulatorConstants.TEXT);
        FileLoaderUtil.populateProperties(SimulatorConstants.CONFIG_PATH, SimulatorConstants.PROPERTIES);

        ValidateFile(filename);

        gridSetupService = new GridSetupServiceImpl(filename[0]);
        gridSetupService.loadSquares();
        gridSetupService.display();

       }

    private void ValidateFile(String[] filename) throws InValidDataException {
        if (filename == null || filename.length == 0 || filename[0] == null) {
            String message = StaticDataLoaderUtil.getConfigValueAsString(SimulatorConstants.ErrorCode_E1003);
            throw new InValidDataException(SimulatorConstants.ErrorCode_E1003 , message);
        }
    }

    private void setUpExceptionController() {
        GlobalExceptionController globalExceptionController = new GlobalExceptionController();
        Thread.setDefaultUncaughtExceptionHandler(globalExceptionController);
    }

}

package com.mylearning.service.impl;


import com.mylearning.constants.CostItems;
import com.mylearning.constants.SimulatorConstants;
import com.mylearning.exception.InValidDataException;
import com.mylearning.model.Coordinate;
import com.mylearning.constants.Land;

import com.mylearning.model.Simulator;
import com.mylearning.model.Square;
import com.mylearning.service.GridSetupService;
import com.mylearning.utils.FileLoaderUtil;
import com.mylearning.utils.StaticDataLoaderUtil;

import java.util.Arrays;
import java.util.Objects;

public class GridSetupServiceImpl implements GridSetupService {

    private String fileName;

    public GridSetupServiceImpl(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Process data from file and loads site Map
     * @throws InValidDataException
     */
    @Override
    public void loadSquares() throws InValidDataException {

        try {
            String[] rows = FileLoaderUtil.readFileLinesAsStreams(this.fileName).toArray(String[]::new);
            int rowCount = (int) Arrays.stream(rows).filter(row -> !row.isEmpty()).count();
            int columnCount = !rows[0].isEmpty() ? rows[0].length() : 0;
            Simulator.squares = new Square[rowCount][columnCount];

            for (int i = 0; i < rows.length; i++) {
                char[] lands = rows[i].toCharArray();
                for (int j = 0; j < lands.length; j++) {
                    Square square = new Square();
                    square.setCoordinate(new Coordinate(i, j));
                    square.setLand(Land.valueOf(String.valueOf(lands[j])));
                    trackInitialUnClearedSquares(square.getLand());
                    Simulator.squares[i][j] = square;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            String message = StaticDataLoaderUtil.getConfigValueAsString(SimulatorConstants.ErrorCode_E1002);
            throw new InValidDataException(SimulatorConstants.ErrorCode_E1002, message);
        }
    }

    /**
     * Displays the site Map with the Simulation static text
     */
    @Override
    public void display() {

        StaticDataLoaderUtil.populateStaticText(SimulatorConstants.WELCOME_TEXT);
        displaySquares();
        StaticDataLoaderUtil.populateStaticText(SimulatorConstants.PRE_OPERATION_TEXT);
        StaticDataLoaderUtil.populateStaticTextinLine(SimulatorConstants.AVAILABLE_OPERATIONS_TEXT);

    }

    /**
     * Displays the site Map
     */
    @Override
    public void displaySquares() {

        Arrays.stream(Simulator.squares).forEach(squares1 -> {
            System.out.println();
            Arrays.stream(squares1)
                    .filter(Objects::nonNull)
                    .map(Square::getLand)
                    .forEach(land -> System.out.print(land + "\t"));
        });

    }

    private void trackInitialUnClearedSquares(Land land){
        if (Land.T != land) {
            CostItems.unClearedSquaresLabel.incrementQuantity(1);
        }
    }
}

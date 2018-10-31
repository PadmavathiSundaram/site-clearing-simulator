package com.mylearning.service;

import com.mylearning.exception.InValidDataException;

/**
 * Handles Site map Loading for the simulation
 */
public interface GridSetupService {

    void loadSquares() throws InValidDataException;
    void displaySquares();
    void display();
}

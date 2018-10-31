package com.mylearning.service;


import com.mylearning.exception.FatalException;

/**
 * Tracks and displays Cost
 */
public interface CostService {
    void updateCost(boolean isDestination) throws FatalException;
    void incrementCommunicationOverhead();
    int displayCost();
}

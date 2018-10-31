package com.mylearning.service;


import com.mylearning.exception.FatalException;
import com.mylearning.strategy.NavigationStrategy;

/**
 * Handles Operations based on Navigation Strategy
 */
public interface OperationService {
    boolean operate(NavigationStrategy navigationStrategy) throws FatalException;
}

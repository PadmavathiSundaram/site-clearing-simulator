package com.mylearning.constants;

import com.mylearning.strategy.NavigationStrategy;
import com.mylearning.strategy.impl.AdvanceNavigationStrategyImpl;
import com.mylearning.strategy.impl.LeftNavigationStrategyImpl;
import com.mylearning.strategy.impl.QuitStrategyImpl;
import com.mylearning.strategy.impl.RightNavigationStrategyImpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Bunch of Supported Operations
 */
public enum Operations {

    A("advance"),
    R("right") {
        @Override
        public NavigationStrategy getNavigationStrategy() {
            return new RightNavigationStrategyImpl();
        }
    },
    L("left") {
        @Override
        public NavigationStrategy getNavigationStrategy() {
            return new LeftNavigationStrategyImpl();
        }
    },
    Q("quit") {
        @Override
        public NavigationStrategy getNavigationStrategy() {
            return new QuitStrategyImpl();
        }
    };

    private String operation;

    public String getOperation() {
        return operation;
    }

    Operations(String operation) {
        this.operation = operation;
    }

    private static final Map<String, Operations> nameIndex = new HashMap<>();

    static {
        for (Operations operation : Operations.values()) {
            nameIndex.put(operation.name().toLowerCase(), operation);
        }
    }

    public static Operations getIfPresent(String operation) {

        Optional<Operations> matchByValue = lookupByValue(operation);
        if (matchByValue.isPresent()) {
            return matchByValue.get();
        } else {
            return lookupByName(operation);
        }
    }

    public NavigationStrategy getNavigationStrategy() {
        return new AdvanceNavigationStrategyImpl();
    }



    private static Optional<Operations> lookupByValue(final String operation) {

        Stream<Operations> matchByValue = Arrays.stream(Operations.values())
                .filter(operations -> {return operations.operation.equalsIgnoreCase(operation);});

        return matchByValue.findFirst();

    }

    private static Operations lookupByName(String name) {
        if ( name == null || name.isEmpty()) {
            return null;
        }
        return nameIndex.get(name.toLowerCase());
    }


}

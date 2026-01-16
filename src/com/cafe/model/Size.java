package com.cafe.model;

public class Size {
    private final String name;
    private final double multiplier;

    public Size(String name, double multiplier) {
        this.name = name;
        this.multiplier = multiplier;
    }

    public String getName() { return name; }
    public double getMultiplier() { return multiplier; }

    @Override
    public String toString() { return name + " (x" + String.format("%.2f", multiplier) + ")"; }
}

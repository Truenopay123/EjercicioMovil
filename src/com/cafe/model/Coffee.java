package com.cafe.model;

public class Coffee {
    private final String name;
    private final double basePrice;

    public Coffee(String name, double basePrice) {
        this.name = name;
        this.basePrice = basePrice;
    }

    public String getName() { return name; }
    public double getBasePrice() { return basePrice; }

    @Override
    public String toString() { return name + " ($" + String.format("%.2f", basePrice) + ")"; }
}

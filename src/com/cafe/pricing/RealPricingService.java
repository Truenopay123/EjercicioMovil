package com.cafe.pricing;

import com.cafe.model.Order;

public class RealPricingService implements PricingService {
    @Override
    public double calculatePrice(Order order) {
        double base = order.getCoffee().getBasePrice();
        double sizeMultiplier = order.getSize().getMultiplier();
        double toppingsSum = order.getToppings().stream().mapToDouble(t -> t.getPrice()).sum();
        double total = base * sizeMultiplier + toppingsSum;
        return round2(total);
    }

    private double round2(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}

package com.cafe.pricing;

import com.cafe.model.FoodOrder;
import com.cafe.model.Order;

/**
 * Sujeto real del Proxy:
 * - Implementa la lógica pura de cálculo sin reglas transversales.
 * - El Proxy puede envolver esta clase para añadir validaciones o promociones.
 */
public class RealPricingService implements PricingService {
    @Override
    public double calculatePrice(Order order) {
        double base = order.getCoffee().getBasePrice();
        double sizeMultiplier = order.getSize().getMultiplier();
        double toppingsSum = order.getToppings().stream().mapToDouble(t -> t.getPrice()).sum();
        double total = base * sizeMultiplier + toppingsSum;
        return round2(total);
    }

    @Override
    public double calculatePrice(FoodOrder order) {
        double total = order.getFood().getPrice() * order.getQuantity();
        return round2(total);
    }

    private double round2(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}

package com.cafe.pricing;

import com.cafe.model.Order;

public class PricingServiceProxy implements PricingService {
    private final PricingService real;

    public PricingServiceProxy(PricingService real) {
        this.real = real;
    }

    @Override
    public double calculatePrice(Order order) {
        if (order == null || order.getCoffee() == null || order.getSize() == null) {
            throw new IllegalArgumentException("Orden inválida");
        }
        double total = real.calculatePrice(order);
        // Regla de variabilidad: promoción del 10% si hay 3 o más toppings
        if (order.getToppings().size() >= 3) {
            total = total * 0.90; // 10% descuento
        }
        // Protección: nunca negativo
        if (total < 0) total = 0;
        return Math.round(total * 100.0) / 100.0;
    }
}

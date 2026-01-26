package com.cafe.pricing;

import com.cafe.model.FoodOrder;
import com.cafe.model.Order;

/**
 * Patrón Proxy:
 * - Esta clase actúa como "sustituto" (proxy) de un {@link PricingService} real.
 * - Controla el acceso al servicio real (validaciones, reglas adicionales,
 *   logging, caché, seguridad, etc.) sin que el cliente tenga que saberlo.
 * - Aquí delegamos el cálculo base a {@code real} y luego aplicamos
 *   variaciones de precio (promos) y salvaguardas.
 */
public class PricingServiceProxy implements PricingService {
    private final PricingService real;

    public PricingServiceProxy(PricingService real) {
        this.real = real;
    }

    @Override
    public double calculatePrice(Order order) {
        // Validación previa (responsabilidad extra del Proxy):
        // aseguramos que la orden sea consistente antes de tocar el servicio real.
        if (order == null || order.getCoffee() == null || order.getSize() == null) {
            throw new IllegalArgumentException("Orden inválida");
        }
        // Delegación: el precio base lo calcula el sujeto real.
        double total = real.calculatePrice(order);

        // Variabilidad del precio (regla de negocio adicional en el Proxy):
        // Aplicamos un 10% de descuento si el cliente elige 3 o más toppings.
        if (order.getToppings().size() >= 3) {
            total = total * 0.90; // 10% de descuento
        }

        // Protección adicional (también típica en un Proxy): nunca permitir totales negativos.
        if (total < 0) total = 0;
        return Math.round(total * 100.0) / 100.0;
    }

    @Override
    public double calculatePrice(FoodOrder order) {
        if (order == null || order.getFood() == null || order.getQuantity() <= 0) {
            throw new IllegalArgumentException("Orden de comida inválida");
        }
        double total = real.calculatePrice(order);
        if (total < 0) total = 0;
        return Math.round(total * 100.0) / 100.0;
    }
}

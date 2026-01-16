package com.cafe.mediator;

import com.cafe.catalog.Catalog;
import com.cafe.catalog.UserRegistry;
import com.cafe.model.*;
import com.cafe.pricing.PricingServiceProxy;
import com.cafe.pricing.RealPricingService;

import java.util.ArrayList;
import java.util.List;

public class CafeMediator {
    private final Catalog catalog = Catalog.getInstance();
    private final UserRegistry registry = UserRegistry.getInstance();
    private final PricingServiceProxy pricing = new PricingServiceProxy(new RealPricingService());

    public User registerUser(String name, String email) {
        return registry.register(name, email);
    }

    public List<Coffee> listCoffees() { return catalog.getCoffees(); }
    public List<Size> listSizes() { return catalog.getSizes(); }
    public List<Topping> listToppings() { return catalog.getToppings(); }

    public Order createOrder(User user, String coffeeName, String sizeName, List<String> toppingNames) {
        Coffee coffee = catalog.findCoffee(coffeeName);
        Size size = catalog.findSize(sizeName);
        List<Topping> selected = new ArrayList<>();
        if (toppingNames != null) {
            for (String tName : toppingNames) {
                Topping t = catalog.findTopping(tName);
                if (t != null) selected.add(t);
            }
        }
        if (coffee == null || size == null) {
            throw new IllegalArgumentException("Café o tamaño no existe en el catálogo");
        }
        return new Order(user, coffee, size, selected);
    }

    public double priceOrder(Order order) {
        return pricing.calculatePrice(order);
    }
}

package com.cafe.mediator;

import com.cafe.catalog.Catalog;
import com.cafe.catalog.UserRegistryProxy;
import com.cafe.model.*;
import com.cafe.pricing.RealPricingService;

import java.util.ArrayList;
import java.util.List;

/**
 * Patrón Mediator:
 * - Centraliza y coordina la interacción entre subsistemas: catálogo (Singleton),
 *   registro de usuarios (Singleton con Proxy) y precios (servicio real).
 * - Reduce el acoplamiento: la aplicación cliente (consola) solo habla con el Mediator.
 */
public class CafeMediator {
    // Dependencias internas. Nótese que Catalog y UserRegistry son Singletons.
    private final Catalog catalog = Catalog.getInstance();
    // Usamos un Proxy para el registro de usuarios (validaciones, prevención duplicados)
    private final UserRegistryProxy registry = new UserRegistryProxy(com.cafe.catalog.UserRegistry.getInstance());
    // Cálculo de precios usa directamente el servicio real.
    private final RealPricingService pricing = new RealPricingService();

    // Operación de alto nivel expuesta al cliente: el Mediator delega y coordina.
    public User registerUser(String name, String email) {
        return registry.register(name, email);
    }

    // Catálogo café
    public List<Coffee> listCoffees() { return catalog.getCoffees(); }
    public List<Size> listSizes() { return catalog.getSizes(); }
    public List<Topping> listToppings() { return catalog.getToppings(); }

    // Catálogo comida
    public List<Food> listFoods() { return catalog.getFoods(); }

    // Crea orden de café
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
            throw new IllegalArgumentException("Cafe o tamaño no existe en el catalogo");
        }
        return new Order(user, coffee, size, selected);
    }

    // Crea orden de comida
    public FoodOrder createFoodOrder(User user, String foodName, int quantity) {
        Food food = catalog.findFood(foodName);
        if (food == null) {
            throw new IllegalArgumentException("La comida no existe en el catalogo");
        }
        return new FoodOrder(user, food, quantity);
    }

    // Precio final café
    public double priceOrder(Order order) { return pricing.calculatePrice(order); }

    // Precio final comida
    public double priceOrder(FoodOrder order) { return pricing.calculatePrice(order); }
}

package com.cafe.controller;

import com.cafe.mediator.CafeMediator;
import com.cafe.model.Coffee;
import com.cafe.model.Food;
import com.cafe.model.FoodOrder;
import com.cafe.model.Order;
import com.cafe.model.Size;
import com.cafe.model.Topping;
import com.cafe.model.User;

import java.util.List;

/**
 * GRASP Controller:
 * - Centraliza el manejo de eventos del sistema provenientes de la vista (CLI/App).
 * - Coordina operaciones usando el Mediator sin sobrecargar la vista.
 * - Mantiene estado mínimo (usuario actual) y expone métodos claros.
 */
public class CafeController {
    private final CafeMediator mediator;
    private User currentUser;

    public CafeController(CafeMediator mediator) {
        this.mediator = mediator;
    }

    /** Registra un usuario y lo establece como actual. */
    public User handleRegisterUser(String name, String email) {
        currentUser = mediator.registerUser(name, email);
        return currentUser;
    }

    /** Devuelve el usuario actual (o null si no hay). */
    public User getCurrentUser() {
        return currentUser;
    }

    /** Listado de catálogo de café (delegado al Mediator). */
    public List<Coffee> getCoffees() { return mediator.listCoffees(); }
    public List<Size> getSizes() { return mediator.listSizes(); }
    public List<Topping> getToppings() { return mediator.listToppings(); }

    /** Listado de catálogo de comida. */
    public List<Food> getFoods() { return mediator.listFoods(); }

    /** Crea una orden de café para el usuario actual. */
    public Order handleCreateOrder(String coffeeName, String sizeName, List<String> toppingNames) {
        if (currentUser == null) {
            throw new IllegalStateException("No hay usuario actual. Registre uno primero.");
        }
        return mediator.createOrder(currentUser, coffeeName, sizeName, toppingNames);
    }

    /** Calcula el total para una orden. */
    public double getTotal(Order order) {
        return mediator.priceOrder(order);
    }

    /** Crea una orden de comida para el usuario actual. */
    public FoodOrder handleCreateFoodOrder(String foodName, int quantity) {
        if (currentUser == null) {
            throw new IllegalStateException("No hay usuario actual. Registre uno primero.");
        }
        return mediator.createFoodOrder(currentUser, foodName, quantity);
    }

    /** Calcula el total para una orden de comida. */
    public double getTotal(FoodOrder order) {
        return mediator.priceOrder(order);
    }
}

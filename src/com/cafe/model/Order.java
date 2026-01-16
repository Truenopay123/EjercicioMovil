package com.cafe.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {
    private final User user;
    private final Coffee coffee;
    private final Size size;
    private final List<Topping> toppings;

    public Order(User user, Coffee coffee, Size size, List<Topping> toppings) {
        this.user = user;
        this.coffee = coffee;
        this.size = size;
        this.toppings = new ArrayList<>(toppings != null ? toppings : Collections.emptyList());
    }

    public User getUser() { return user; }
    public Coffee getCoffee() { return coffee; }
    public Size getSize() { return size; }
    public List<Topping> getToppings() { return Collections.unmodifiableList(toppings); }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Orden de ").append(user.getName()).append(": ");
        sb.append(coffee.getName()).append(" - Tamaño ").append(size.getName());
        if (!toppings.isEmpty()) {
            sb.append(" - Toppings: ");
            for (int i = 0; i < toppings.size(); i++) {
                sb.append(toppings.get(i).getName());
                if (i < toppings.size() - 1) sb.append(", ");
            }
        } else {
            sb.append(" - Sin toppings");
        }
        return sb.toString();
    }
}

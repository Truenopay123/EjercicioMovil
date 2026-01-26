package com.cafe.model;

public class FoodOrder {
    private final User user;
    private final Food food;
    private final int quantity;

    public FoodOrder(User user, Food food, int quantity) {
        this.user = user;
        this.food = food;
        this.quantity = Math.max(1, quantity);
    }

    public User getUser() { return user; }
    public Food getFood() { return food; }
    public int getQuantity() { return quantity; }

    @Override
    public String toString() {
        return "Orden de " + user.getName() + ": " + quantity + " x " + food.getName();
    }
}

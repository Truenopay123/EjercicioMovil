package com.cafe.catalog;

import com.cafe.model.Coffee;
import com.cafe.model.Food;
import com.cafe.model.Size;
import com.cafe.model.Topping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Patrón Singleton (instancia única del catálogo):
 * - Constructor privado: evita que otros creen instancias.
 * - Campo estático {@code INSTANCE}: almacena la única instancia.
 * - Método {@link #getInstance()}: punto de acceso global y controlado.
 * - Aquí se usa sincronización simple para inicialización perezosa (lazy).
 */
public class Catalog {
    private static Catalog INSTANCE;

    private final List<Coffee> coffees = new ArrayList<>();
    private final List<Food> foods = new ArrayList<>();
    private final List<Size> sizes = new ArrayList<>();
    private final List<Topping> toppings = new ArrayList<>();

    // Constructor privado: impide new Catalog() fuera de esta clase.
    private Catalog() {
        // Cafés con precio base (para tamaño pequeño)
        coffees.add(new Coffee("Americano", 2.00));
        coffees.add(new Coffee("Latte", 2.50));
        coffees.add(new Coffee("Capuccino", 2.80));

        // Comida con precio unitario
        foods.add(new Food("Sandwich", 3.50));
        foods.add(new Food("Muffin", 2.20));
        foods.add(new Food("Cookie", 1.50));

        // Tamaños con multiplicador
        sizes.add(new Size("Pequeño", 1.00));
        sizes.add(new Size("Mediano", 1.30));
        sizes.add(new Size("Grande", 1.60));

        // Toppings con precio fijo
        toppings.add(new Topping("Extra shot", 0.70));
        toppings.add(new Topping("Caramelo", 0.50));
        toppings.add(new Topping("Vainilla", 0.50));
        toppings.add(new Topping("Leche de almendra", 0.60));
    }

    // Punto de acceso global con inicialización perezosa.
    // synchronized garantiza seguridad básica en escenarios concurrentes.
    public static synchronized Catalog getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Catalog();
        }
        return INSTANCE;
    }

    public List<Coffee> getCoffees() { return Collections.unmodifiableList(coffees); }
    public List<Food> getFoods() { return Collections.unmodifiableList(foods); }
    public List<Size> getSizes() { return Collections.unmodifiableList(sizes); }
    public List<Topping> getToppings() { return Collections.unmodifiableList(toppings); }

    public Coffee findCoffee(String name) {
        return coffees.stream().filter(c -> c.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public Food findFood(String name) {
        return foods.stream().filter(f -> f.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public Size findSize(String name) {
        return sizes.stream().filter(s -> s.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public Topping findTopping(String name) {
        return toppings.stream().filter(t -> t.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}

package com.cafe.app;

import com.cafe.mediator.CafeMediator;
import com.cafe.model.Order;
import com.cafe.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // La app cliente solo interactúa con el Mediator.
        // El Mediator coordina el uso del Catálogo y del Registro (ambos Singletons)
        // y del servicio de Precios a través de un Proxy.
        CafeMediator mediator = new CafeMediator();
        User currentUser = null;

        System.out.println("=== Café Console ===");
        boolean running = true;
        while (running) {
            System.out.println("\nMenú:");
            System.out.println("1) Registrar usuario");
            System.out.println("2) Ver catálogo");
            System.out.println("3) Crear orden");
            System.out.println("4) Salir");
            System.out.print("Elige opción: ");
            String option = scanner.nextLine().trim();

            switch (option) {
                case "1":
                    System.out.print("Nombre: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Email: ");
                    String email = scanner.nextLine().trim();
                    // El Mediator delega el registro al Singleton UserRegistry
                    currentUser = mediator.registerUser(name, email);
                    System.out.println("Registrado: " + currentUser);
                    break;
                case "2":
                    System.out.println("\nCafés:");
                    mediator.listCoffees().forEach(c -> System.out.println("- " + c));
                    System.out.println("\nTamaños:");
                    mediator.listSizes().forEach(s -> System.out.println("- " + s));
                    System.out.println("\nToppings:");
                    mediator.listToppings().forEach(t -> System.out.println("- " + t));
                    break;
                case "3":
                    if (currentUser == null) {
                        System.out.println("Primero registra un usuario (opción 1).");
                        break;
                    }
                    System.out.println("\nElige café (escribe el nombre exacto):");
                    mediator.listCoffees().forEach(c -> System.out.println("- " + c.getName()));
                    System.out.print("> ");
                    String coffeeName = scanner.nextLine().trim();

                    System.out.println("\nElige tamaño:");
                    mediator.listSizes().forEach(s -> System.out.println("- " + s.getName()));
                    System.out.print("> ");
                    String sizeName = scanner.nextLine().trim();

                    System.out.println("\nIngresa toppings separados por coma (o vacío):");
                    mediator.listToppings().forEach(t -> System.out.println("- " + t.getName()));
                    System.out.print("> ");
                    String toppingsLine = scanner.nextLine().trim();
                    List<String> toppingNames = new ArrayList<>();
                    if (!toppingsLine.isEmpty()) {
                        for (String part : toppingsLine.split(",")) {
                            String tName = part.trim();
                            if (!tName.isEmpty()) toppingNames.add(tName);
                        }
                    }
                    try {
                        // El Mediator crea la orden consultando el Catálogo (Singleton)
                        Order order = mediator.createOrder(currentUser, coffeeName, sizeName, toppingNames);
                        // El precio se calcula vía Proxy: aplica reglas de variabilidad (p.ej. descuento por toppings)
                        double price = mediator.priceOrder(order);
                        System.out.println("\n" + order);
                        System.out.println("Total: $" + String.format("%.2f", price));
                    } catch (IllegalArgumentException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                    break;
                case "4":
                    running = false;
                    System.out.println("Adiós!");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }
}

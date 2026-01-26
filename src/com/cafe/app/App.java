package com.cafe.app;

import com.cafe.controller.CafeController;
import com.cafe.mediator.CafeMediator;
import com.cafe.model.Order;
import com.cafe.model.User;

import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    private static final Scanner scanner = new Scanner(new InputStreamReader(System.in, StandardCharsets.UTF_8));

    public static void main(String[] args) {
        // Asegurar salida UTF-8 para que la consola muestre acentos y ñ correctamente
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
            System.setErr(new PrintStream(System.err, true, "UTF-8"));
        } catch (UnsupportedEncodingException ignored) { }

        // La vista (CLI) delega la coordinación al Controller (GRASP),
        // que a su vez usa el Mediator para hablar con los subsistemas.
        CafeMediator mediator = new CafeMediator();
        CafeController controller = new CafeController(mediator);

        System.out.println("=== Café & Comida Console ===");
        boolean running = true;
        while (running) {
            System.out.println("\nMenú principal:");
            System.out.println("1) Registrar usuario");
            System.out.println("2) Comida");
            System.out.println("3) Café");
            System.out.println("4) Salir");
            System.out.print("Elige opción: ");
            String option = scanner.nextLine().trim();

            switch (option) {
                case "1":
                    System.out.print("Nombre: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Email: ");
                    String email = scanner.nextLine().trim();
                    // El Controller coordina el registro de usuario.
                    User registered = controller.handleRegisterUser(name, email);
                    System.out.println("Registrado: " + registered);
                    break;
                case "2":
                    // Submenú Comida
                    boolean foodMenu = true;
                    while (foodMenu) {
                        System.out.println("\nComida:");
                        System.out.println("1) Ver catálogo de comida");
                        System.out.println("2) Hacer pedido de comida");
                        System.out.println("3) Volver");
                        System.out.print("Elige opción: ");
                        String fOpt = scanner.nextLine().trim();
                        switch (fOpt) {
                            case "1":
                                System.out.println("\nComidas:");
                                controller.getFoods().forEach(f -> System.out.println("- " + f));
                                break;
                            case "2":
                                if (controller.getCurrentUser() == null) {
                                    System.out.println("Primero registra un usuario (opción 1).");
                                    break;
                                }
                                System.out.println("\nElige comida (nombre exacto):");
                                controller.getFoods().forEach(f -> System.out.println("- " + f.getName()));
                                System.out.print("> ");
                                String foodName = scanner.nextLine().trim();
                                System.out.print("Cantidad (entero >=1): ");
                                String qtyLine = scanner.nextLine().trim();
                                int qty = 1;
                                try { qty = Integer.parseInt(qtyLine); } catch (NumberFormatException ignored) {}
                                try {
                                    var foodOrder = controller.handleCreateFoodOrder(foodName, qty);
                                    double price = controller.getTotal(foodOrder);
                                    System.out.println("\n" + foodOrder);
                                    System.out.println("Total: $" + String.format("%.2f", price));
                                } catch (IllegalArgumentException | IllegalStateException ex) {
                                    System.out.println("Error: " + ex.getMessage());
                                }
                                break;
                            case "3":
                                foodMenu = false;
                                break;
                            default:
                                System.out.println("Opción inválida.");
                        }
                    }
                    break;
                case "3":
                    // Submenú Café
                    boolean coffeeMenu = true;
                    while (coffeeMenu) {
                        System.out.println("\nCafé:");
                        System.out.println("1) Ver catálogo de café");
                        System.out.println("2) Hacer pedido de café");
                        System.out.println("3) Volver");
                        System.out.print("Elige opción: ");
                        String cOpt = scanner.nextLine().trim();
                        switch (cOpt) {
                            case "1":
                                System.out.println("\nCafes:");
                                controller.getCoffees().forEach(c -> System.out.println("- " + c));
                                System.out.println("\nTamaños:");
                                controller.getSizes().forEach(s -> System.out.println("- " + s));
                                System.out.println("\nToppings:");
                                controller.getToppings().forEach(t -> System.out.println("- " + t));
                                break;
                            case "2":
                                if (controller.getCurrentUser() == null) {
                                    System.out.println("Primero registra un usuario (opción 1).");
                                    break;
                                }
                                System.out.println("\nElige café (nombre exacto):");
                                controller.getCoffees().forEach(c -> System.out.println("- " + c.getName()));
                                System.out.print("> ");
                                String coffeeName = scanner.nextLine().trim();

                                System.out.println("\nElige tamaño:");
                                controller.getSizes().forEach(s -> System.out.println("- " + s.getName()));
                                System.out.print("> ");
                                String sizeName = scanner.nextLine().trim();

                                System.out.println("\nIngresa toppings separados por coma (o vacío):");
                                controller.getToppings().forEach(t -> System.out.println("- " + t.getName()));
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
                                    Order order = controller.handleCreateOrder(coffeeName, sizeName, toppingNames);
                                    double price = controller.getTotal(order);
                                    System.out.println("\n" + order);
                                    System.out.println("Total: $" + String.format("%.2f", price));
                                } catch (IllegalArgumentException | IllegalStateException ex) {
                                    System.out.println("Error: " + ex.getMessage());
                                }
                                break;
                            case "3":
                                coffeeMenu = false;
                                break;
                            default:
                                System.out.println("Opción inválida.");
                        }
                    }
                    break;
                case "4":
                    running = false;
                    System.out.println("¡Adiós!");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }
}

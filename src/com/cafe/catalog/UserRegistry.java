package com.cafe.catalog;

import com.cafe.model.User;

import java.util.Map;

/**
 * Patrón Singleton (único registro de usuarios):
 * - Centraliza el almacenamiento y búsqueda de usuarios.
 * - Misma estructura que {@code Catalog}: constructor privado + getInstance().
 */
public class UserRegistry {
    private static UserRegistry INSTANCE;
    private final UserRepository repository;

    // Constructor privado: no se puede instanciar desde fuera.
    private UserRegistry() {
        this.repository = new InMemoryUserRepository();
    }

    // Acceso global a la única instancia.
    public static synchronized UserRegistry getInstance() {
        if (INSTANCE == null) INSTANCE = new UserRegistry();
        return INSTANCE;
    }

    public User register(String name, String email) {
        User user = new User(name, email);
        repository.save(user);
        return user;
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Map<String, User> getAll() { return repository.findAll(); }
}

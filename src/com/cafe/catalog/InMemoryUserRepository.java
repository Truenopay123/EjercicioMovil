package com.cafe.catalog;

import com.cafe.model.User;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementación sencilla en memoria: simula una base de datos usando un mapa.
 */
public class InMemoryUserRepository implements UserRepository {
    private final Map<String, User> storage = new HashMap<>();

    @Override
    public void save(User user) {
        if (user == null) return;
        String email = user.getEmail();
        if (email == null) return;
        storage.put(email.toLowerCase(), user);
    }

    @Override
    public User findByEmail(String email) {
        if (email == null) return null;
        return storage.get(email.toLowerCase());
    }

    @Override
    public Map<String, User> findAll() {
        return Collections.unmodifiableMap(storage);
    }
}

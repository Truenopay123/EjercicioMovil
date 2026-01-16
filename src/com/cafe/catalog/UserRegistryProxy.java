package com.cafe.catalog;

import com.cafe.model.User;

import java.util.Map;

/**
 * Patrón Proxy aplicado al registro de usuarios:
 * - Envuelve a {@link UserRegistry} para añadir validaciones y lógica transversa
 *   (por ejemplo, validación de email y prevención de duplicados) sin tocar
 *   la implementación real.
 */
public class UserRegistryProxy {
    private final UserRegistry real;

    public UserRegistryProxy(UserRegistry real) {
        this.real = real;
    }

    public User register(String name, String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email vacío");
        }
        String normalized = email.trim().toLowerCase();
        if (!normalized.contains("@") || normalized.startsWith("@") || normalized.endsWith("@")) {
            throw new IllegalArgumentException("Email inválido");
        }
        User existing = real.findByEmail(normalized);
        if (existing != null) {
            // Retornamos el usuario existente en vez de crear duplicado.
            return existing;
        }
        return real.register(name, normalized);
    }

    public User findByEmail(String email) {
        if (email == null) return null;
        return real.findByEmail(email.trim().toLowerCase());
    }

    public Map<String, User> getAll() { return real.getAll(); }
}

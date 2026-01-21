package com.cafe.catalog;

import com.cafe.model.User;

import java.util.Map;

/**
 * Repository que abstrae el acceso a los usuarios.
 * Permite reemplazar la fuente de datos sin afectar a los clientes.
 */
public interface UserRepository {
    void save(User user);
    User findByEmail(String email);
    Map<String, User> findAll();
}

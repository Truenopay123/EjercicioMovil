package com.cafe.catalog;

import com.cafe.model.User;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UserRegistry {
    private static UserRegistry INSTANCE;
    private final Map<String, User> usersByEmail = new HashMap<>();

    private UserRegistry() {}

    public static synchronized UserRegistry getInstance() {
        if (INSTANCE == null) INSTANCE = new UserRegistry();
        return INSTANCE;
    }

    public User register(String name, String email) {
        User user = new User(name, email);
        usersByEmail.put(email.toLowerCase(), user);
        return user;
    }

    public User findByEmail(String email) {
        return usersByEmail.get(email.toLowerCase());
    }

    public Map<String, User> getAll() { return Collections.unmodifiableMap(usersByEmail); }
}

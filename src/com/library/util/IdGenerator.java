package com.library.util;

import java.util.HashMap;
import java.util.Map;

public class IdGenerator {
    private static Map<String, Integer> counters = new HashMap<>();

    public static int generateId(String key) {
        int id = counters.getOrDefault(key, 0) + 1;
        counters.put(key, id);
        return id;
    }
}

package io.github.zaragozamartin91.contrazt.main;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MapPropertyTest {
    Map<String, Object> container = new HashMap<String, Object>() {{
        put("one", 1L);
        put("two", 2L);
        put("three", 3L);
    }};
    String key = "three";
    String nameSuffix = "name_suffix";
    MapProperty mapProperty = new MapProperty(container, key, nameSuffix);

    @Test
    void getContainer() {
        assertEquals(container, mapProperty.getContainer());
    }

    @Test
    void getValue() {
        Optional<?> optValue = mapProperty.getValue();
        assertTrue(optValue.isPresent());
        Object value = optValue.get();
        assertEquals(container.get(key), value);

        assertFalse(new MapProperty(container, "missing_key", nameSuffix).getValue().isPresent());
    }

    @Test
    void getName() {
        assertEquals(String.format("{%s}%s", key, nameSuffix), mapProperty.getName());
    }

    @Test
    void getKey() {
        assertEquals(key, mapProperty.getKey());
    }
}
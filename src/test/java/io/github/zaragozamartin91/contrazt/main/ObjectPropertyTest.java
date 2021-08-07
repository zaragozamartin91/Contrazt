package io.github.zaragozamartin91.contrazt.main;

import io.github.zaragozamartin91.contrazt.main.pojo.PojoWithSingleProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class ObjectPropertyTest {
    String fieldValue = "some_value";
    String fieldName = "field0";
    Object container = new PojoWithSingleProperty(fieldValue);
    Field field;
    String containerName = "container_name";
    ObjectProperty objectProperty;

    @BeforeEach
    public void init() throws NoSuchFieldException {
        field = container.getClass().getDeclaredField(fieldName);
        objectProperty = new ObjectProperty(container, field, containerName);
    }

    @Test
    void getContainer() {
        assertEquals(container, objectProperty.getContainer());
    }

    @Test
    void getValue() {
        assertTrue(objectProperty.getValue().filter(fieldValue::equals).isPresent());
        assertFalse(new ObjectProperty(null, field, containerName).getValue().isPresent());
    }

    @Test
    void getName() {
        assertEquals(containerName + "." + fieldName, objectProperty.getName());
    }

    @Test
    void getKey() {
        assertEquals(fieldName, objectProperty.getKey());
    }
}
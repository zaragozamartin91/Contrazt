package io.github.zaragozamartin91.contrazt.main;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CollectionPropertyTest {
    private final String value0 = "one";
    private final String value1 = "two";
    private final String value2 = "three";
    List<String> container = Arrays.asList(value0, value1, value2);
    Integer index = 1;
    String nameSuffix = "name_suffix";
    CollectionProperty collectionProperty = new CollectionProperty(container, index, nameSuffix);

    @Test
    void getContainer() {
        assertEquals(container, collectionProperty.getContainer());
    }

    @Test
    void getValue() {
        Optional<?> optValue = collectionProperty.getValue();
        assertTrue(optValue.isPresent());
        Object value = optValue.get();
        assertEquals(value1, value);

        assertFalse(new CollectionProperty(null, index, nameSuffix).getValue().isPresent());

        assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () -> new CollectionProperty(container, container.size(), nameSuffix).getValue());
    }


    @Test
    void getName() {
        String expectedName = String.format("%s[%d]", nameSuffix, index);
        assertEquals(expectedName, collectionProperty.getName());
    }

    @Test
    void getKey() {
        assertEquals("" + index, collectionProperty.getKey());
    }
}
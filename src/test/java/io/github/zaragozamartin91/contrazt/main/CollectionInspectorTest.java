package io.github.zaragozamartin91.contrazt.main;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CollectionInspectorTest {
    List<String> container = Arrays.asList("one", "two", "three");
    String containerName = "container_name";
    CollectionInspector collectionInspector = new CollectionInspector(container, containerName);

    @Test
    void getProperties() {
        List<ContainerProperty> properties = collectionInspector.getProperties();
        assertEquals(container.size(), properties.size());

        List<String> values = Arrays.asList("one", "two", "three");
        List<String> propertyNames = Arrays.asList("[0]container_name", "[1]container_name", "[2]container_name");
        for (int i = 0; i < properties.size(); i++) {
            ContainerProperty containerProperty = properties.get(i);
            Object value = containerProperty.getValue().orElse(null);
            String propertyName = containerProperty.getName();
            assertEquals(values.get(i), value);
            assertEquals(propertyNames.get(i), propertyName);
        }
    }
}
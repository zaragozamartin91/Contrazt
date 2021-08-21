package io.github.zaragozamartin91.contrazt.main;

import io.github.zaragozamartin91.contrazt.main.pojo.PojoWithMixedProperties;
import io.github.zaragozamartin91.contrazt.main.pojo.PojoWithSingleProperty;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ObjectInspectorTest {
    Object container = new PojoWithMixedProperties("one", 2, 3L);
    String containerName = "container_name";
    ObjectInspector objectInspector = new ObjectInspector(container, containerName);

    @Test
    void getProperties() {
        List<ContainerProperty> properties = objectInspector.getProperties();
        System.out.println("Properties found: " + properties.stream().map(ContainerProperty::getName).collect(Collectors.toList()));
        assertEquals(3, properties.size());
        assertTrue(properties.stream().allMatch(ContainerProperty::isAtom));

        List<? extends Serializable> values = Arrays.asList("one", 2, 3L);
        List<String> fieldNames = Arrays.asList("container_name.field0", "container_name.field1", "container_name.field2");
        for (int i = 0; i < properties.size(); i++) {
            ContainerProperty containerProperty = properties.get(i);
            Object value = containerProperty.getValue().orElse(null);
            String name = containerProperty.getName();
            assertEquals(values.get(i), value);
            assertEquals(fieldNames.get(i), name);
        }
    }
}
package io.github.zaragozamartin91.contrazt.main;

import io.github.zaragozamartin91.contrazt.main.pojo.PojoWithMixedProperties;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class SmartContainerInspectorTest {
    String containerName = "container_name";
    Object objContainer = new PojoWithMixedProperties("one", 2, 3L);
    List<String> listContainer = Arrays.asList("one", "two", "three");
    Map<String, Object> mapContainer = new HashMap<String, Object>() {{
        put("one", "value_1");
        put("two", "value_2");
        put("three", "value_3");
    }};

    @Test
    void getObjProperties() {
        List<ContainerProperty> properties = new SmartContainerInspector(objContainer, containerName).getProperties();
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

    @Test
    void getCollectionProperties() {
        List<ContainerProperty> properties = new CollectionInspector(listContainer, containerName).getProperties();
        assertEquals(listContainer.size(), properties.size());

        List<String> values = Arrays.asList("one", "two", "three");
        List<String> propertyNames = Arrays.asList("container_name[0]", "container_name[1]", "container_name[2]");
        for (int i = 0; i < properties.size(); i++) {
            ContainerProperty containerProperty = properties.get(i);
            Object value = containerProperty.getValue().orElse(null);
            String propertyName = containerProperty.getName();
            assertEquals(values.get(i), value);
            assertEquals(propertyNames.get(i), propertyName);
        }
    }

    @Test
    void getMapProperties() {
        List<ContainerProperty> properties = new MapInspector(mapContainer, containerName).getProperties();
        assertEquals(mapContainer.entrySet().size(), properties.size());

        Map<String, ? extends Optional<?>> propertiesByName =
                properties.stream().collect(Collectors.toMap(ContainerProperty::getName, ContainerProperty::getValue));
        Object one = propertiesByName.get("container_name{one}").orElse(null);
        Object two = propertiesByName.get("container_name{two}").orElse(null);
        Object three = propertiesByName.get("container_name{three}").orElse(null);
        assertEquals("value_1", one);
        assertEquals("value_2", two);
        assertEquals("value_3", three);
    }
}
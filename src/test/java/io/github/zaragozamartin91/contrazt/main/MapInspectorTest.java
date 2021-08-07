package io.github.zaragozamartin91.contrazt.main;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class MapInspectorTest {
    Map<String, Object> container = new HashMap<String, Object>() {{
       put("one", "value_1");
       put("two", "value_2");
       put("three", "value_3");
    }};
    String containerName = "container_name";
    MapInspector mapInspector = new MapInspector(container, containerName);

    @Test
    void getProperties() {
        List<ContainerProperty> properties = mapInspector.getProperties();
        assertEquals(container.entrySet().size(), properties.size());

        Map<String, ? extends Optional<?>> propertiesByName =
                properties.stream().collect(Collectors.toMap(ContainerProperty::getName, ContainerProperty::getValue));
        Object one = propertiesByName.get("{one}container_name").orElse(null);
        Object two = propertiesByName.get("{two}container_name").orElse(null);
        Object three = propertiesByName.get("{three}container_name").orElse(null);
        assertEquals("value_1", one);
        assertEquals("value_2", two);
        assertEquals("value_3", three);
    }
}
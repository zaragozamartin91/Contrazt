package io.github.zaragozamartin91.contrazt.main;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Returns properties of a given Map instance
 */
public class MapInspector implements ContainerInspector {
    private final Map<?, Object> container;
    private final String containerName;

    public MapInspector(Map<?, Object> container, String containerName) {
        this.container = container;
        this.containerName = containerName;
    }

    @Override
    public List<ContainerProperty> getProperties() {
        // obtain map properties and map to propper Container Property
        Set<? extends Map.Entry<?, Object>> entries = Optional.ofNullable(container).map(Map::entrySet).orElse(new HashSet<>());
        return entries.stream().map(entry -> new MapProperty(container, entry.getKey(), containerName)).collect(Collectors.toList());
    }
}

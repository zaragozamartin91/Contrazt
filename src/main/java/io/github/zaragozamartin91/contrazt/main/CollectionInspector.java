package io.github.zaragozamartin91.contrazt.main;

import java.util.*;

/**
 * Returns properties of a given Collection instance
 */
public class CollectionInspector implements ContainerInspector {
    private final Collection<?> container;
    private final String containerName;

    public CollectionInspector(Collection<?> container, String containerName) {
        this.container = container;
        this.containerName = containerName;
    }

    @Override
    public List<ContainerProperty> getProperties() {
        Collection<?> entries = Optional.ofNullable(container).orElse(new ArrayList<>());
        List<ContainerProperty> containerProperties = new ArrayList<>();
        for (int i = 0; i < entries.size(); i++) {
            containerProperties.add(new CollectionProperty(container, i, containerName));
        }
        return Collections.unmodifiableList(containerProperties);
    }
}

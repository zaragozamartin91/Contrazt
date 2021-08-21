package io.github.zaragozamartin91.contrazt.main;

import java.util.*;

/**
 * Inspects container type and decides which sub container inspector to use to get container's properties
 */
public class SmartContainerInspector {
    private final Object container;
    private final String containerName;

    public SmartContainerInspector(Object container, String containerName) {
        this.container = container;
        this.containerName = containerName;
    }

    public List<ContainerProperty> getProperties() {
        ContainerInspector properInspector = getProperInspector();
        return properInspector.getProperties();
    }

    private ContainerInspector getProperInspector() {
        if(container == null) {
            return new VoidContainerInspector();
        }

        Class<?> containerType = container.getClass();

        if(Collection.class.isAssignableFrom(containerType)) {
            return new CollectionInspector((Collection<?>) container, containerName);
        }

        if(Map.class.isAssignableFrom(containerType)) {
            return new MapInspector((Map<?, Object>) container, containerName);
        }

        return new ObjectInspector(container, containerName);
    }
}

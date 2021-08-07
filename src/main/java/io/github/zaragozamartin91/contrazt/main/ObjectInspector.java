package io.github.zaragozamartin91.contrazt.main;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Returns properties of a given POJO
 */
public class ObjectInspector implements ContainerInspector {
    private final Object container;
    private final GetClassFields getClassFields = GetClassFields.DEFAULT;
    private final Class<?> containerType;
    private final String containerName;

    public ObjectInspector(Object container, String containerName) {
        this.container = Optional.ofNullable(container)
                .orElseThrow(() -> new IllegalArgumentException("Container cannot be null"));
        this.containerType = container.getClass();
        this.containerName = containerName;
    }

    public ObjectInspector(Class<?> containerType, String containerName) {
        this.containerType = containerType;
        this.containerName = containerName;
        container = null;
    }

    @Override
    public List<ContainerProperty> getProperties() {
        List<Field> containerFields = getClassFields.apply(containerType);
        return containerFields.stream().map(cf -> new ObjectProperty(container, cf, containerName)).collect(Collectors.toList());
    }
}

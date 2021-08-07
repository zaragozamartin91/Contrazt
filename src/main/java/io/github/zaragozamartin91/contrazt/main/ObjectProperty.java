package io.github.zaragozamartin91.contrazt.main;

import java.lang.reflect.Field;
import java.util.Optional;

public class ObjectProperty implements ContainerProperty {
    private final Object container;
    private final Field field;
    private final String containerName;

    public ObjectProperty(Object container, Field field, String containerName) {
        this.container = container;
        this.field = field;
        this.containerName = containerName;
    }

    @Override
    public Object getContainer() {
        return container;
    }

    @Override
    public Optional<?> getValue() {
        return Optional.ofNullable(container).map(c -> {
            try {
                field.setAccessible(true);
                return field.get(c);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Error while getting field value " + field.getName() + " of container " + container, e);
            }
        });
    }

    @Override
    public String getName() {
        return String.format("%s.%s", containerName, field.getName());
    }

    @Override
    public String getKey() {
        return field.getName();
    }
}

package io.github.zaragozamartin91.contrazt.main;

import java.util.Map;
import java.util.Optional;

public class MapProperty implements ContainerProperty {
    private final Map<?, Object> container;
    private final Object key;
    private final String containerName;

    public MapProperty(Map<?, Object> container, Object key, String containerName) {
        this.container = container;
        this.key = key;
        this.containerName = containerName;
    }

    @Override
    public Map<?, Object> getContainer() {
        return container;
    }

    @Override
    public Optional<?> getValue() {
        return Optional.ofNullable(container).map(c -> c.get(key));
    }

    @Override
    public String getName() {
        return String.format("%s{%s}", containerName, key);
    }

    @Override
    public String getKey() {
        return key.toString();
    }
}

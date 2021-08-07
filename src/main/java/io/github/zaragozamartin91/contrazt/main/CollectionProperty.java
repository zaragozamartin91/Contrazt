package io.github.zaragozamartin91.contrazt.main;

import java.util.Collection;
import java.util.Optional;

public class CollectionProperty implements ContainerProperty {
    private final int index;
    private final Collection<?> container;
    private final String nameSuffix;

    public CollectionProperty(Collection<?> container, Integer index, String nameSuffix) {
        this.index = Optional.of(index).filter(i -> i >= 0)
                .orElseThrow(() -> new IllegalArgumentException("Index has to be equal or higher than 0"));
        this.container = container;
        this.nameSuffix = nameSuffix;
    }

    @Override
    public Optional<?> getValue() {
        return Optional.ofNullable(container).map(c -> {
            Object[] indexedContainer = c.toArray();
            return indexedContainer[index];
        });
    }

    @Override
    public String getName() {
        return String.format("[%d]%s", index, nameSuffix);
    }

    @Override
    public String getKey() {
        return String.valueOf(index);
    }
}

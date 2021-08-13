package io.github.zaragozamartin91.contrazt.main;

import java.util.Optional;

public class ContainerPropertyStub implements ContainerProperty {
    private final Object value;
    private final String name;
    private final String key;

    public ContainerPropertyStub(String name, Object value, String key) {
        this.value = value;
        this.name = name;
        this.key = key;
    }

    public ContainerPropertyStub(String name, Object value) {
        this.value = value;
        this.name = name;
        this.key = "";
    }

    @Override
    public Object getContainer() {
        return null; // no parent container
    }

    @Override
    public Optional<?> getValue() {
        return Optional.ofNullable(value);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public boolean isAtom() {
        return true;
    }
}

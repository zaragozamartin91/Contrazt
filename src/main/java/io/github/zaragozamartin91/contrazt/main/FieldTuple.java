package io.github.zaragozamartin91.contrazt.main;

import java.util.Objects;

public class FieldTuple {
    private final Class<?> type;
    private final Object value;

    public FieldTuple(Class<?> type, Object value) {
        this.type = type;
        this.value = value;
    }

    public Class<?> getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public boolean sameTypeAs(FieldTuple other) {
        return this.type.equals(other.type);
    }

    public boolean differentTypeAs(FieldTuple other) {
        return !sameTypeAs(other);
    }

    public boolean sameValueAs(FieldTuple other) {
        return Objects.equals(value, other.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldTuple that = (FieldTuple) o;
        return Objects.equals(type, that.type) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }
}

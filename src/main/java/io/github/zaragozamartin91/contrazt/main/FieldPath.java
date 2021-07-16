package io.github.zaragozamartin91.contrazt.main;

import java.lang.reflect.Field;

/**
 * Holds the field and it's path within a class
 */
public class FieldPath {
    private final Field field;
    private final String path;

    public FieldPath(Field field, String path) {
        this.field = field;
        this.path = path;
    }

    public Field getField() {
        return field;
    }

    public String getPath() {
        return path;
    }

    public boolean voidFieldPath() {
        return path == null || path.isEmpty();
    }
}

package io.github.zaragozamartin91.contrazt.main;

import java.lang.reflect.Field;


class GetFieldValueByName {
    static final GetFieldValueByName DEFAULT = new GetFieldValueByName();


    Maybe<FieldTuple> apply(Object obj, String fieldName) {
        Class<?> clazz = obj.getClass();

        // we check on declared fields first, then on super-classes
        return GetClassFields.NO_SUPER_FIELDS.apply(clazz).stream()
                .filter(f -> fieldMatches(f, fieldName))
                .findAny()
                .map(f -> field2tuple(f, obj))
                .orElseGet(() -> GetClassFields.DEFAULT.apply(clazz).stream()
                        .filter(f -> fieldMatches(f, fieldName))
                        .findAny()
                        .map(f -> field2tuple(f, obj))
                        .orElse(Maybe.empty()));
    }

    private Maybe<FieldTuple> field2tuple(Field field, Object obj) {
        field.setAccessible(true);
        Object value = Try.<Field, Object>unchecked(ff -> ff.get(obj)).apply(field);
        return Maybe.of(new FieldTuple(value.getClass(), value));
    }

    private boolean fieldMatches(Field f, String fieldName) {
        return fieldName.equals(f.getName());
    }
}

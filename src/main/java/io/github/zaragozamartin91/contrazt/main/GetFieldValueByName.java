package io.github.zaragozamartin91.contrazt.main;

import io.github.zaragozamartin91.contrazt.error.AmbiguousFieldException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;


class GetFieldValueByName {
    private final boolean lenient;
    private final Consumer<String> validateFieldName;

    public GetFieldValueByName(boolean lenient, Consumer<String> validateFieldName) {
        this.lenient = lenient;
        this.validateFieldName = validateFieldName;
    }

    Maybe<FieldTuple> apply(Object obj, String fieldName) {
        validateFieldName.accept(fieldName);
        Class<?> clazz = obj.getClass();

        Field[] declaredFields = clazz.getDeclaredFields();

        List<Field> matchingFields = Arrays.stream(declaredFields)
                .filter(f -> fieldMatches(f, fieldName))
                .collect(Collectors.toList());

        switch (matchingFields.size()) {
            case 0:
                return Maybe.empty();
            case 1:
                Field field = matchingFields.get(0);
                field.setAccessible(true);
                Object value = Try.<Field, Object>unchecked(ff -> ff.get(obj)).apply(field);
                return Maybe.of(new FieldTuple(value.getClass(), value));
            default:
                throw new AmbiguousFieldException("There are more than one matching fields with name " + fieldName);
        }
    }

    private boolean fieldMatches(Field f, String fieldName) {
        return lenient ? fieldName.equalsIgnoreCase(f.getName()) : fieldName.equals(f.getName());
    }
}

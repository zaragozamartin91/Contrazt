package io.github.zaragozamartin91.contrazt.usecase;

import io.github.zaragozamartin91.contrazt.error.AmbiguousFieldException;
import io.github.zaragozamartin91.contrazt.main.Maybe;
import io.github.zaragozamartin91.contrazt.util.Try;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;


public class GetFieldValueByName {
    private final boolean lenient;
    private final Consumer<String> validateFieldName;

    public GetFieldValueByName(boolean lenient, Consumer<String> validateFieldName) {
        this.lenient = lenient;
        this.validateFieldName = validateFieldName;
    }

    public <T> Maybe<T> apply(Object obj, String fieldName) {
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
                Object fieldValue = Try.<Field, Object>unchecked(ff -> ff.get(obj)).apply(field);
                return Maybe.of(this.cast(fieldValue));
            default:
                throw new AmbiguousFieldException("There are more than one matching fields with name " + fieldName);
        }
    }

    @SuppressWarnings("unchecked")
    private <U> U cast(Object value) {
        return (U) value;
    }

    private boolean fieldMatches(Field f, String fieldName) {
        return lenient ? fieldName.equalsIgnoreCase(f.getName()) : fieldName.equals(f.getName());
    }
}

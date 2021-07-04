package io.github.zaragozamartin91.contrazt.usecase;

import io.github.zaragozamartin91.contrazt.error.AmbiguousFieldException;
import io.github.zaragozamartin91.contrazt.util.Try;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;


public class GetFieldValueByName {
    private final boolean lenient;
    private final Consumer<String> validateFieldName;

    public GetFieldValueByName(boolean lenient, Consumer<String> validateFieldName) {
        this.lenient = lenient;
        this.validateFieldName = validateFieldName;
    }

    public <T> Optional<T> apply(Object obj, String fieldName) {
        validateFieldName.accept(fieldName);
        Class<?> clazz = obj.getClass();

        Field[] declaredFields = clazz.getDeclaredFields();

        return Arrays.stream(declaredFields)
                .filter(f -> fieldMatches(f, fieldName))
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        ls -> Optional.of(ls)
                                .filter(lss -> lss.size() <= 1)
                                .orElseThrow(() -> new AmbiguousFieldException("There are more than one matching fields with name " + fieldName))
                                .stream()
                                .findFirst()))
                .map(f -> {
                    f.setAccessible(true);
                    return Try.<Field, Object>unchecked(ff -> ff.get(obj)).apply(f);
                }).map(this::cast);

    }

    @SuppressWarnings("unchecked")
    private <U> U cast(Object value) {
        return (U) value;
    }

    private boolean fieldMatches(Field f, String fieldName) {
        return lenient ? fieldName.equalsIgnoreCase(f.getName()) : fieldName.equals(f.getName());
    }
}

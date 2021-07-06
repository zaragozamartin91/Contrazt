package io.github.zaragozamartin91.contrazt.usecase;

import io.github.zaragozamartin91.contrazt.main.Maybe;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class GetNestedFieldValueByName {
    private final boolean lenient;
    private final Consumer<String> validateFieldName;
    private final GetFieldValueByName getFieldByName;

    public GetNestedFieldValueByName(
            boolean lenient,
            Consumer<String> validateFieldName) {
        this.lenient = lenient;
        this.validateFieldName = validateFieldName;
        this.getFieldByName = new GetFieldValueByName(lenient, validateFieldName);
    }

    // search for
    public <T> Maybe<T> apply(Object obj, String fieldName) {
        validateFieldName.accept(fieldName);
        String[] fieldNameSegments = fieldName.split(Pattern.quote("."));
        ArrayDeque<String> fieldNames = new ArrayDeque<>(Arrays.asList(fieldNameSegments));
        return doApply(obj, fieldNames);
    }

    public <T> Maybe<T> doApply(Object currObj, Deque<String> fieldNames) {
        String fieldName = fieldNames.poll();
        return fieldName == null ?
                Maybe.of(currObj).map(this::cast) :
                getFieldByName.apply(currObj, fieldName).flatMap(obj -> doApply(obj, fieldNames));
    }

    @SuppressWarnings("unchecked")
    private <U> U cast(Object value) {
        return (U) value;
    }
}

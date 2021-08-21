package io.github.zaragozamartin91.contrazt.main;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.github.zaragozamartin91.contrazt.main.FieldDiffStatus.*;
import static java.util.stream.Collectors.toList;

public class DoubleContrazt {
    private final Object _first;
    private final Object _second;

    public DoubleContrazt(Object first, Object second) {
        this._first = first;
        this._second = second;
    }

    public SingleContrazt first() {
        return new SingleContrazt(_first);
    }

    public SingleContrazt second() {
        return new SingleContrazt(_second);
    }

    public List<FieldDiff> contrastAllFields() {
        List<ContainerProperty> firstProperties = first().flatten();
        List<ContainerProperty> secondProperties = second().flatten();

        Map<String, ContainerProperty> firstPropertiesByName =
                firstProperties.stream().collect(Collectors.toMap(ContainerProperty::normalizeName, Function.identity()));
        Map<String, ContainerProperty> secondPropertiesByName =
                secondProperties.stream().collect(Collectors.toMap(ContainerProperty::normalizeName, Function.identity()));

        Set<String> allPropertyNames = Stream.concat(
                firstPropertiesByName.keySet().stream(),
                secondPropertiesByName.keySet().stream()
        ).collect(Collectors.toSet());

        return allPropertyNames.stream().map(propertyName -> {
            ContainerProperty firstProperty = firstPropertiesByName.get(propertyName);
            ContainerProperty secondProperty = secondPropertiesByName.get(propertyName);

            return matchProperties(SingleContrazt.removeRoot(propertyName), firstProperty, secondProperty);
        }).collect(toList());
    }

    static FieldDiff matchProperties(String propertyName, ContainerProperty firstProperty, ContainerProperty secondProperty) {
        Class<?> firstType = Optional.ofNullable(firstProperty).flatMap(ContainerProperty::getValueType).orElse(null);
        Object firstValue = Optional.ofNullable(firstProperty).flatMap(ContainerProperty::getValue).orElse(null);

        Class<?> secondType = Optional.ofNullable(secondProperty).flatMap(ContainerProperty::getValueType).orElse(null);
        Object secondValue = Optional.ofNullable(secondProperty).flatMap(ContainerProperty::getValue).orElse(null);

        Function<FieldDiffStatus, FieldDiff> newFieldDiff = fieldDiffStatus ->
                new FieldDiff(propertyName, firstType, firstValue, secondType, secondValue, fieldDiffStatus);

        if (firstProperty == null) {
            return newFieldDiff.apply(MISSING_FIRST);
        } else if (secondProperty == null) {
            return newFieldDiff.apply(MISSING_SECOND);
        } else if (firstValue == null && secondValue == null) {
            return newFieldDiff.apply(EQUALS_NULL);
        } else if (firstValue == null) {
            return newFieldDiff.apply(NULL_FIRST);
        } else if (secondValue == null) {
            return newFieldDiff.apply(NULL_SECOND);
        } else if (Objects.equals(firstValue, secondValue)) {
            // neither value is null
            return newFieldDiff.apply(EQUAL);
        } else if (similarType(firstType, secondType)) {
            // values are not null and not equal ; check if type is similar
            return newFieldDiff.apply(VALUE_MISMATCH);
        } else {
            return newFieldDiff.apply(TYPE_MISMATCH);
        }
    }

    static boolean similarType(Class<?> firstType, Class<?> secondType) {
        return firstType.isAssignableFrom(secondType) || secondType.isAssignableFrom(firstType);
    }
}

package io.github.zaragozamartin91.contrazt.main;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class GetAllNestedFields {
    static final GetAllNestedFields DEFAULT = new GetAllNestedFields(
            new GetClassFields(true, true, true), false);

    private final GetClassFields getClassFields;
    private final boolean keepStatic;

    GetAllNestedFields(GetClassFields getClassFields, boolean keepStatic) {
        this.getClassFields = getClassFields;
        this.keepStatic = keepStatic;
    }

    List<FieldPath> apply(Object object) {
        Class<?> mainType = object.getClass();
        return apply(mainType);
    }

    List<FieldPath> apply(Class<?> type) {
        return getFields(type, new ArrayList<>(), null);
    }

    private List<FieldPath> getFields(Class<?> currType, List<String> fieldPath, Field currField) {
        List<Field> thisFields = getClassFields.apply(currType).stream()
                .filter(this::acceptField)
                .collect(Collectors.toList());

        if (thisFields.isEmpty()) {
            // currType is a "LEAF"
            return Optional.ofNullable(currField)
                    .map(f -> {
                        String fullFieldName = String.join(".", fieldPath);
                        return Collections.singletonList(new FieldPath(currField, fullFieldName));
                    }).orElseGet(ArrayList::new);
        } else {
            // curr type has more fields
            return thisFields.stream().map(field -> {
                String fieldName = field.getName();
                List<String> nextPath = Stream.concat(fieldPath.stream(), Stream.of(fieldName)).collect(Collectors.toList());
                Class<?> fieldType = field.getType();
                return getFields(fieldType, nextPath, field);
            }).flatMap(Collection::stream).collect(Collectors.toList());
        }
    }

    private boolean acceptField(Field field) {
        return !skipField(field);
    }

    private boolean skipField(Field field) {
        return !keepStatic && isStatic(field);
    }

    private boolean isStatic(Field df) {
        return Modifier.isStatic(df.getModifiers());
    }
}

package io.github.zaragozamartin91.contrazt.main;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class GetAllFields {
    private static final Class<?>[] WRAPPER_TYPES = {
            Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Character.TYPE, Boolean.TYPE
    };
    private static final Set<Class<?>> WRAPPER_TYPE_SET =
            Arrays.stream(WRAPPER_TYPES).collect(Collectors.toSet());

    static final GetAllFields DEFAULT = new GetAllFields(
            false,
            true,
            true,
            true);

    private final boolean keepStatic;
    private final boolean skipWrapperTypes;
    private final boolean skipCharSequence;
    private final boolean checkSuperclass;

    GetAllFields(boolean keepStatic,
                 boolean skipWrapperTypes,
                 boolean skipCharSequence, boolean checkSuperclass) {
        this.keepStatic = keepStatic;
        this.skipWrapperTypes = skipWrapperTypes;
        this.skipCharSequence = skipCharSequence;
        this.checkSuperclass = checkSuperclass;
    }

    List<FieldPath> apply(Object object) {
        Class<?> mainType = object.getClass();
        return apply(mainType);
    }

    List<FieldPath> apply(Class<?> type) {
        if (skipType(type)) {
            return Collections.emptyList();
        }

        List<FieldPath> currFields = getFields(type, new ArrayList<>(), null);
        List<FieldPath> allFields = checkSuperclass ?
                Stream.concat(currFields.stream(), apply(type.getSuperclass()).stream()).collect(Collectors.toList()) :
                currFields;
        return allFields.stream()
                .filter(f -> !f.voidFieldPath())
                .collect(Collectors.toList());
    }

    private List<FieldPath> getFields(Class<?> currType, List<String> fieldPath, Field currField) {
        if (acceptType(currType)) {
            Field[] declaredFields = currType.getDeclaredFields();
            List<Field> okFields = Arrays.stream(declaredFields).filter(this::acceptField).collect(Collectors.toList());
            if (okFields.isEmpty()) {
                // type has no more fields, build field name and pop stack
                return getFields(null, fieldPath, currField);
            } else {
                // type has more fields, need to go deeper
                return okFields.stream().map(field -> {
                    String fieldName = field.getName();
                    List<String> nextPath = Stream.concat(fieldPath.stream(), Stream.of(fieldName)).collect(Collectors.toList());
                    Class<?> fieldType = field.getType();
                    return getFields(fieldType, nextPath, field);
                }).flatMap(Collection::stream).collect(Collectors.toList());
            }
        } else {
            // type is primitive or wrapper, build field name and pop stack
            String fullFieldName = String.join(".", fieldPath);
            return Collections.singletonList(new FieldPath(currField, fullFieldName));
        }
    }

    private boolean acceptType(Class<?> currType) {
        return !skipType(currType);
    }

    private boolean skipType(Class<?> currType) {
        return currType == null
                || currType.isPrimitive()
                || (skipWrapperTypes && isWrapperType(currType))
                || (skipCharSequence && CharSequence.class.isAssignableFrom(currType));
    }

    private boolean isWrapperType(Class<?> type) {
        return WRAPPER_TYPE_SET.stream().anyMatch(s -> s.isAssignableFrom(type));
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

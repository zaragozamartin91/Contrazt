package io.github.zaragozamartin91.contrazt.main;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class GetClassFields {
    private static final Class<?>[] WRAPPER_TYPES = {
            Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Character.TYPE, Boolean.TYPE
    };
    private static final Set<Class<?>> WRAPPER_TYPE_SET =
            Arrays.stream(WRAPPER_TYPES).collect(Collectors.toSet());

    static final GetClassFields DEFAULT =
            new GetClassFields(true, true, true);
    static final GetClassFields NO_SUPER_FIELDS =
            new GetClassFields(true, true, true);

    private final boolean checkSuperclass;
    private final boolean skipWrapperTypes;
    private final boolean skipCharSequence;

    GetClassFields(boolean checkSuperclass,
                   boolean skipWrapperTypes,
                   boolean skipCharSequence) {
        this.checkSuperclass = checkSuperclass;
        this.skipWrapperTypes = skipWrapperTypes;
        this.skipCharSequence = skipCharSequence;
    }

    List<Field> apply(Class<?> typeClass) {
        if (skipType(typeClass)) {
            return new ArrayList<>();
        }

        List<Field> thisFields = Arrays.stream(typeClass.getDeclaredFields())
                .filter(this::keepField)
                .collect(Collectors.toList());

        if (checkSuperclass) {
            List<Field> superFields = apply(typeClass.getSuperclass());
            return Stream.concat(thisFields.stream(), superFields.stream()).collect(Collectors.toList());
        }

        return thisFields;
    }

    private boolean skipType(Class<?> currType) {
        return currType == null
                || currType.isPrimitive()
                || (skipWrapperTypes && isWrapperType(currType))
                || (skipCharSequence && CharSequence.class.isAssignableFrom(currType))
                || currType.isSynthetic();
    }

    private boolean isWrapperType(Class<?> type) {
        return WRAPPER_TYPE_SET.stream().anyMatch(s -> s.isAssignableFrom(type));
    }

    private boolean keepField(Field field) {
        return !Modifier.isStatic(field.getModifiers()) && !field.isSynthetic();
    }
}

package io.github.zaragozamartin91.contrazt.main;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// TODO : add tests!
class GetClassFields {
    private static final Predicate<Class<?>> IS_ATOM = new IsAtom();

    static final GetClassFields DEFAULT = new GetClassFields(true);

    private final boolean checkSuperclass;

    GetClassFields(boolean checkSuperclass) {
        this.checkSuperclass = checkSuperclass;
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
        } else {
            return thisFields;
        }
    }

    private boolean skipType(Class<?> currType) {
        return currType == null || IS_ATOM.test(currType);
    }

    private boolean keepField(Field field) {
        return !Modifier.isStatic(field.getModifiers()) && !field.isSynthetic();
    }
}

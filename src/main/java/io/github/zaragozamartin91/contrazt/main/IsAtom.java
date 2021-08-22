package io.github.zaragozamartin91.contrazt.main;

import java.time.temporal.TemporalAccessor;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Checks if a type is an ATOM , that is to say, holds no relevant fields to be inspected
 */
class IsAtom implements Predicate<Class<?>> {
    private static final Class<?>[] WRAPPER_TYPES = {
            Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Character.class, Boolean.class
    };
    private static final Set<Class<?>> WRAPPER_TYPE_SET = Arrays.stream(WRAPPER_TYPES).collect(Collectors.toSet());

    private static final Class<?>[] ATOMIC_TYPES = {
            CharSequence.class, Number.class, Date.class, TemporalAccessor.class, Calendar.class
    };
    private static final Set<Class<?>> ATOMIC_TYPE_SET = Arrays.stream(ATOMIC_TYPES).collect(Collectors.toSet());


    @Override
    public boolean test(Class<?> currType) {
        return currType.isPrimitive()
                || isWrapperType(currType)
                || isAtomicType(currType)
                || currType.isSynthetic();
    }

    private boolean isWrapperType(Class<?> type) {
        return WRAPPER_TYPE_SET.stream().anyMatch(s -> s.isAssignableFrom(type));
    }

    private boolean isAtomicType(Class<?> type) {
        return ATOMIC_TYPE_SET.stream().anyMatch(s -> s.isAssignableFrom(type));
    }
}

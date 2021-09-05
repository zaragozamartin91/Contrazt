package io.github.zaragozamartin91.contrazt.main;

import java.sql.Timestamp;
import java.time.temporal.TemporalAccessor;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

enum AtomType {
    ; // Holds constants for atomic types

    private static final Class<?>[] WRAPPER_TYPES = {
            Byte.class,
            Short.class,
            Integer.class,
            Long.class,
            Float.class,
            Double.class,
            Character.class,
            Boolean.class
    };
    static final Set<Class<?>> WRAPPERS = Arrays.stream(WRAPPER_TYPES).collect(Collectors.toSet());

    private static final Class<?>[] ATOMIC_TYPES = {
            CharSequence.class,
            Number.class,
            Date.class,
            TemporalAccessor.class,
            Calendar.class,
            Timestamp.class
    };
    static final Set<Class<?>> ATOMS = Arrays.stream(ATOMIC_TYPES).collect(Collectors.toSet());
}

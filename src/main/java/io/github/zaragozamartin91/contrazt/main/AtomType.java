package io.github.zaragozamartin91.contrazt.main;

import java.sql.Timestamp;
import java.time.temporal.TemporalAccessor;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

enum AtomType {
    ; // Holds constants for atomic types

    private static final Class<?>[] DEFAULT_WRAPPER_TYPES = {
            Byte.class,
            Short.class,
            Integer.class,
            Long.class,
            Float.class,
            Double.class,
            Character.class,
            Boolean.class
    };
    static final Set<Class<?>> WRAPPERS = Arrays.stream(DEFAULT_WRAPPER_TYPES).collect(Collectors.toSet());

    private static final Class<?>[] DEFAULT_ATOMIC_TYPES = {
            CharSequence.class,
            Number.class,
            Date.class,
            TemporalAccessor.class,
            Calendar.class,
            Timestamp.class
    };
    static final Set<Class<?>> ATOMS;

    static {
        LoadAtomTypesFromResourceFile loadAtomTypesFromResourceFile = new LoadAtomTypesFromResourceFile();
        Set<Class<?>> customAtomicTypes = loadAtomTypesFromResourceFile.get();
        ATOMS = Stream.concat(Arrays.stream(DEFAULT_ATOMIC_TYPES), customAtomicTypes.stream()).collect(Collectors.toSet());
    }
}

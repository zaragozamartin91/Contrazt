package io.github.zaragozamartin91.contrazt.main;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public interface ContainerProperty {
    Class<?>[] WRAPPER_TYPES = {
            Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Character.class, Boolean.class
    };
    Set<Class<?>> WRAPPER_TYPE_SET =
            Arrays.stream(WRAPPER_TYPES).collect(Collectors.toSet());

    default boolean isAtom() {
        Class<?> valueType = getValue().map(Object::getClass).orElse(null);
        return valueType == null
                || valueType.isPrimitive()
                || WRAPPER_TYPE_SET.stream().anyMatch(t -> t.isAssignableFrom(valueType))
                || CharSequence.class.isAssignableFrom(valueType);
    }

    /**
     * Gets this property's container
     *
     * @return the property's container
     */
    Object getContainer();

    /**
     * Returns the property's value if exists
     *
     * @return property's value if exists
     */
    Optional<?> getValue();

    /**
     * Returns fully qualified name of this property
     *
     * @return property's name
     */
    String getName();

    /**
     * Returns index or key for this property within it's container
     *
     * @return property's index key
     */
    String getKey();
}

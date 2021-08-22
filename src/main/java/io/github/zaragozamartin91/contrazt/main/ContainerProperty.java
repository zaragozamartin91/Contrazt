package io.github.zaragozamartin91.contrazt.main;

import java.util.Optional;
import java.util.function.Predicate;

public interface ContainerProperty {
    Predicate<Class<?>> isAtom = new IsAtom();

    // TODO : add Date and LocalDate
    default boolean isAtom() {
        Class<?> valueType = getValue().map(Object::getClass).orElse(null);
        return valueType == null || isAtom.test(valueType);
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

    default Optional<Class<?>> getValueType() {
        return getValue().map(Object::getClass);
    }

    /**
     * Replaces maps references with dots
     * E.G.: first.second{third}.fourth[0] BECOMES first.second.third.fourth[0]
     *
     * @return Normalized container property name
     */
    default String normalizeName() {
        String name = getName();
        return name.replaceAll("[{]", ".").replaceAll("[}]", "");
    }
}

package io.github.zaragozamartin91.contrazt.main;

import java.util.Optional;

public interface ContainerProperty {
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

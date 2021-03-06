package io.github.zaragozamartin91.contrazt.main;

import java.util.List;
import java.util.regex.Pattern;

public class SingleContrazt {
    public static final String DEFAULT_ROOT_NAME = "&ROOT";

    private final Object obj;

    public SingleContrazt(Object obj) {this.obj = obj;}

    /**
     * Flattens a structure
     *
     * @param rootName Root name of the structure to flatten
     * @return Flattened structure (Object / Map / List) using rootName as the top level name
     */
    public List<ContainerProperty> flatten(String rootName) {
        MasterContainerInspector inspector = new MasterContainerInspector(obj, rootName);
        return inspector.getProperties();
    }

    /**
     * Flattens a structure using {@code &ROOT} as the top level name
     *
     * @return Flattened structure (Object / Map / List) using {@code &ROOT} as the top level name
     */
    public List<ContainerProperty> flatten() {
        return flatten(DEFAULT_ROOT_NAME);
    }

    public static String removeRoot(String rootName, String propertyName) {
        return propertyName.replaceAll(Pattern.quote(rootName + "."), "");
    }

    public static String removeRoot(String propertyName) {
        return removeRoot(DEFAULT_ROOT_NAME, propertyName);
    }

    public DoubleContrazt and(Object right) {
        return new DoubleContrazt(obj, right);
    }
}

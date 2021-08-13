package io.github.zaragozamartin91.contrazt.main;

import java.util.List;

public class SingleContrazt {
    private static final String DEFAULT_ROOT_NAME = "&ROOT";

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
     * Flattens a structure using &ROOT as the top level name
     *
     * @return Flattened structure (Object / Map / List) using &ROOT as the top level name
     */
    public List<ContainerProperty> flatten() {
        return flatten(DEFAULT_ROOT_NAME);
    }

    /**
     * Gets the value of a field or nested field
     *
     * @param fieldNames Field name chain specifying the nested field to which get it's value
     * @return Field value or {@code Optional.empty()} if field non existent or null.
     */
    public Maybe<FieldTuple> getValue(String... fieldNames) {
        String fieldName = String.join(".", fieldNames);
        GetFieldValueByPath usecase = GetFieldValueByPath.DEFAULT;
        return usecase.apply(obj, fieldName);
    }

    public DoubleContrazt and(Object right) {
        return new DoubleContrazt(obj, right);
    }
}

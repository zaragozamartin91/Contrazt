package io.github.zaragozamartin91.contrazt.main;

public class SingleContrazt {
    private final Object obj;

    public SingleContrazt(Object obj) {this.obj = obj;}

    /**
     * Gets the value of a field or nested field
     * @param fieldNames Field name chain specifying the nested field to which get it's value
     * @return Field value or {@code Optional.empty()} if field non existent or null.
     */
    public Maybe<FieldTuple> getValue(String... fieldNames) {
        String fieldName = String.join(".", fieldNames);
        GetNestedFieldValueByName usecase = GetNestedFieldValueByName.DEFAULT;
        return usecase.apply(obj, fieldName);
    }

    public DoubleContrazt and(Object right) {
        return new DoubleContrazt(obj, right);
    }
}

package io.github.zaragozamartin91.contrazt.main;

import io.github.zaragozamartin91.contrazt.usecase.GetFieldValueByName;
import io.github.zaragozamartin91.contrazt.usecase.ValidateFieldName;

public class SingleContrazt {
    private final Object obj;

    public SingleContrazt(Object obj) {this.obj = obj;}

    /**
     * Gets the value of a field or nested field
     * @param fieldNames Field name chain specifying the nested field to which get it's value
     * @param <T> Expected field value type
     * @return Field value or {@code Optional.empty()} if field non existent or null.
     */
    public <T> Maybe<T> getValue(String... fieldNames) {
        String fieldName = String.join(".", fieldNames);
        GetFieldValueByName usecase = new GetFieldValueByName(false, new ValidateFieldName());
        return usecase.apply(obj, fieldName);
    }

    public DoubleContrazt and(Object right) {
        return new DoubleContrazt(obj, right);
    }
}

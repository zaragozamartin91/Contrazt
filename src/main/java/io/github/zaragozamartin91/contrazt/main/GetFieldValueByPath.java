package io.github.zaragozamartin91.contrazt.main;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.regex.Pattern;

public class GetFieldValueByPath {
    static GetFieldValueByPath DEFAULT = new GetFieldValueByPath(GetFieldValueByName.DEFAULT);

    private final GetFieldValueByName getFieldByName;

    public GetFieldValueByPath(GetFieldValueByName getFieldByName) {this.getFieldByName = getFieldByName;}

    // search for
    Maybe<FieldTuple> apply(Object obj, String fieldPath) {
        String[] fieldNameSegments = fieldPath.split(Pattern.quote("."));
        ArrayDeque<String> fieldNames = new ArrayDeque<>(Arrays.asList(fieldNameSegments));
        return doApply(obj, fieldNames, null);
    }

    private Maybe<FieldTuple> doApply(Object currObj, Deque<String> fieldNames, FieldTuple result) {
        String fieldName = fieldNames.poll();
        return fieldName == null ?
                Maybe.of(result) :
                getFieldByName.apply(currObj, fieldName)
                        .flatMap(fieldValue -> doApply(fieldValue.getValue(), fieldNames, fieldValue));
    }
}

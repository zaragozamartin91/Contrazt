package io.github.zaragozamartin91.contrazt.main;

import java.util.List;

import static io.github.zaragozamartin91.contrazt.main.FieldDiffStatus.*;
import static java.util.stream.Collectors.toList;

public class DoubleContrazt {
    private final Object _first;
    private final Object _second;

    public DoubleContrazt(Object first, Object second) {
        this._first = first;
        this._second = second;
    }

    public SingleContrazt first() {
        return new SingleContrazt(_first);
    }

    public SingleContrazt second() {
        return new SingleContrazt(_second);
    }

    public DoubleContrazt reverse() {
        return new DoubleContrazt(_first, _second);
    }

    public FieldDiff compareFields(String... fieldNames) {
        Maybe<FieldTuple> first = first().getValue(fieldNames);
        Maybe<FieldTuple> second = second().getValue(fieldNames);

        String stdFieldName = String.join(".", fieldNames);

        FieldTuple fv = first.orNull();
        FieldTuple sv = second.orNull();

        return first.missing() && second.missing() ? new FieldDiff(stdFieldName, fv, sv, NOT_FOUND)
                : first.exists() && second.missing() ? new FieldDiff(stdFieldName, fv, sv, MISSING_SECOND)
                : first.missing() && second.exists() ? new FieldDiff(stdFieldName, fv, sv, MISSING_FIRST)
                : first.get().differentTypeAs(second.get()) ? new FieldDiff(stdFieldName, fv, sv, TYPE_MISMATCH)
                : first.get().sameValueAs(second.get()) ? new FieldDiff(stdFieldName, fv, sv, EQUAL)
                : new FieldDiff(stdFieldName, fv, sv, VALUE_MISMATCH);
    }

    public List<FieldDiff> compareAllFields() {
        GetAllNestedFields getAllNestedFields = GetAllNestedFields.DEFAULT;
        List<String> fieldNames = getAllNestedFields.apply(_first).stream().map(FieldPath::getPath).collect(toList());
        return fieldNames.stream().map(this::compareFields).collect(toList());
    }
}

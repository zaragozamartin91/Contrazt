package io.github.zaragozamartin91.contrazt.main;

import java.util.List;
import java.util.stream.Collectors;

import static io.github.zaragozamartin91.contrazt.main.FieldDiffStatus.*;

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
        GetAllFieldNames getAllFieldNames = new GetAllFieldNames(false, true, true);
        List<String> fieldNames = getAllFieldNames.apply(_first);
        return fieldNames.stream().map(this::compareFields).collect(Collectors.toList());
    }
}

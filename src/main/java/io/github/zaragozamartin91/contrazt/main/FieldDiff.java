package io.github.zaragozamartin91.contrazt.main;

public class FieldDiff {
    private final String fieldName;
    private final FieldTuple first;
    private final FieldTuple second;
    private final FieldDiffStatus result;

    public FieldDiff(String fieldName, FieldTuple first, FieldTuple second, FieldDiffStatus result) {
        this.fieldName = fieldName;
        this.first = first;
        this.second = second;
        this.result = result;
    }

    public String getFieldName() {
        return fieldName;
    }

    public FieldTuple getFirst() {
        return first;
    }

    public FieldTuple getSecond() {
        return second;
    }

    public FieldDiffStatus getResult() {
        return result;
    }
}

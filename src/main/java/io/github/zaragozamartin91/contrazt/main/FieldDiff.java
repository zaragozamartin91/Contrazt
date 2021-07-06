package io.github.zaragozamartin91.contrazt.main;

public class FieldDiff {
    private final String fieldName;
    private final Object first;
    private final Object second;
    private final FieldDiffResult result;

    public FieldDiff(String fieldName, Object first, Object second, FieldDiffResult result) {
        this.fieldName = fieldName;
        this.first = first;
        this.second = second;
        this.result = result;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFirst() {
        return first;
    }

    public Object getSecond() {
        return second;
    }

    public FieldDiffResult getResult() {
        return result;
    }
}

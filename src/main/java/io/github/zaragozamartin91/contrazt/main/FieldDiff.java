package io.github.zaragozamartin91.contrazt.main;

import java.util.Objects;

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

    public FieldDiff(String fieldName,
                     Class<?> firstType,
                     Object firstValue,
                     Class<?> secondType,
                     Object secondValue,
                     FieldDiffStatus result) {
        this.fieldName = fieldName;
        this.first = new FieldTuple(firstType, firstValue);
        this.second = new FieldTuple(secondType, secondValue);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldDiff fieldDiff = (FieldDiff) o;
        return Objects.equals(fieldName, fieldDiff.fieldName) && Objects.equals(first, fieldDiff.first) && Objects.equals(second, fieldDiff.second) && result == fieldDiff.result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldName, first, second, result);
    }

    @Override
    public String toString() {
        return "FieldDiff{" +
                "fieldName='" + fieldName + '\'' +
                ", first=" + first +
                ", second=" + second +
                ", result=" + result +
                '}';
    }
}

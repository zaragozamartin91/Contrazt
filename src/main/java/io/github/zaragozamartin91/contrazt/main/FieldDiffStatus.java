package io.github.zaragozamartin91.contrazt.main;

public enum FieldDiffStatus {
    /**
     * Field exists on both objects and their values are equal
     */
    EQUAL,
    /**
     * Field is missing on both objects
     */
    NOT_FOUND,
    /**
     * Field is present and their values are NOT equal
     */
    VALUE_MISMATCH,
    /**
     * Field types are different
     */
    TYPE_MISMATCH,
    /**
     * Field does not exist on the left side, but is present on the right side
     */
    MISSING_LEFT,
    /**
     * Field does exist on the left side, but is not present on the right side
     */
    MISSING_RIGHT;
}

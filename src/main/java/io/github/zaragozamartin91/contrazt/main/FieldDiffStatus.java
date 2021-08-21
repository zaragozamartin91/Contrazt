package io.github.zaragozamartin91.contrazt.main;

public enum FieldDiffStatus {
    /**
     * Field exists on both objects , their values are equal and NOT NULL
     */
    EQUAL,
    /**
     * Field exists on both objects and both values are NULL
     */
    EQUALS_NULL,
    /**
     * Field is missing on both objects
     */
    NOT_FOUND,
    /**
     * Field exists on both objects, their types are similar but their values are different
     */
    VALUE_MISMATCH,
    /**
     * Field types are different
     */
    TYPE_MISMATCH,
    /**
     * Field does not exist on the first object, but is present on the second object
     */
    MISSING_FIRST,
    /**
     * Field does exist on the first object, but is not present on the second object
     */
    MISSING_SECOND,
    /**
     * Field exists on both objects, but the first object's value is NULL
     */
    NULL_FIRST,
    /**
     * Field exists on both objects, but the second object's value is NULL
     */
    NULL_SECOND
}

package io.github.zaragozamartin91.usecase;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;


import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidateFieldNameTest {
    private final ValidateFieldName usecase = new ValidateFieldName();

    @Test
    public void validFieldNamesAreAccepted() {
        Field[] declaredFields = Bar.class.getDeclaredFields();

        List<Executable> executables = Arrays.stream(declaredFields)
                .map(Field::getName)
                .map(fn -> ((Executable) () -> assertDoesNotThrow(() -> usecase.accept(fn))))
                .collect(Collectors.toList());

        Assertions.assertAll(executables);
    }

    @Test
    public void acceptThrowsIllegalArgumentExceptionOnInvalidFieldName() {
        assertThrows(IllegalArgumentException.class, () -> usecase.accept(""));
        assertThrows(IllegalArgumentException.class, () -> usecase.accept("  "));
        assertThrows(IllegalArgumentException.class, () -> usecase.accept(null));
    }

    static class Bar {
        private String someFieldWithMeaning;
        private long $thisField_hasWeirdCharacters;
        private int _thisFieldStartsWithUnderscore;
        private final BigDecimal THIS_FIELD_CONTAINS_ALL_CAPS = BigDecimal.ZERO;
    }
}
package io.github.zaragozamartin91.contrazt.usecase;


import io.github.zaragozamartin91.contrazt.error.AmbiguousFieldException;
import io.github.zaragozamartin91.contrazt.util.Try;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class GetFieldValueByNameTest {
    private GetFieldValueByName usecase;

    @Test
    public void applyFindsFieldByStrictMatchingName() {
        ValidateFieldName validateFieldName = mock(ValidateFieldName.class);
        usecase = new GetFieldValueByName(false, validateFieldName);

        Foo foo = new Foo("foo", 123L);

        Field[] declaredFields = foo.getClass().getDeclaredFields();
        Arrays.stream(declaredFields)
                .forEach(field -> {
                    field.setAccessible(true);
                    Optional<?> result = usecase.apply(foo, field.getName());
                    Object fieldValue = Try.<Field, Object>unchecked(f -> f.get(foo)).apply(field);
                    assertTrue(result.isPresent());
                    assertEquals(fieldValue, result.get());
                });

        verify(validateFieldName, times(declaredFields.length)).accept(anyString());
    }

    @Test
    public void applyFindsFieldByLenientMatchingName() {
        ValidateFieldName validateFieldName = mock(ValidateFieldName.class);
        usecase = new GetFieldValueByName(true, validateFieldName);

        Foo foo = new Foo("foo", 123L);

        Field[] declaredFields = foo.getClass().getDeclaredFields();
        Arrays.stream(declaredFields)
                .forEach(field -> {
                    field.setAccessible(true);
                    Optional<?> result = usecase.apply(foo, field.getName().toUpperCase());
                    Object fieldValue = Try.<Field, Object>unchecked(f -> f.get(foo)).apply(field);
                    assertTrue(result.isPresent());
                    assertEquals(fieldValue, result.get());
                });

        verify(validateFieldName, times(declaredFields.length)).accept(anyString());
    }

    @Test
    public void applyThrowsAmbiguousFieldExceptionOnFieldsWithSameLowercaseNames() {
        ValidateFieldName validateFieldName = mock(ValidateFieldName.class);
        usecase = new GetFieldValueByName(true, validateFieldName);

        Lorem lorem = new Lorem("foo", 2L, 3);

        assertThrows(AmbiguousFieldException.class, () -> usecase.apply(lorem, "id"));
    }

    @Test
    public void applyReturnsEmptyOnMissingFields() {
        usecase = new GetFieldValueByName(false, mock(ValidateFieldName.class));

        Foo foo = new Foo("foo", 123L);

        Optional<Object> result = usecase.apply(foo, "missing_Field");
        assertFalse(result.isPresent());
    }

    static class Foo {
        String name;
        long id;

        public Foo(String name, long id) {
            this.name = name;
            this.id = id;
        }
    }

    static class Lorem {
        String name;
        long id;
        int Id;

        public Lorem(String name, long id, int id1) {
            this.name = name;
            this.id = id;
            Id = id1;
        }
    }
}
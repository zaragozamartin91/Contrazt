package io.github.zaragozamartin91.contrazt.main;


import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


public class GetFieldValueByNameTest {
    private GetFieldValueByName usecase;

    @Test
    public void applyFindsFieldByStrictMatchingName() {
        usecase = GetFieldValueByName.DEFAULT;

        Foo foo = new Foo("foo", 123L);

        Field[] declaredFields = foo.getClass().getDeclaredFields();
        Arrays.stream(declaredFields)
                .forEach(field -> {
                    field.setAccessible(true);
                    Optional<FieldTuple> result = usecase.apply(foo, field.getName()).toOptional();
                    Object fieldValue = Try.<Field, Object>unchecked(f -> f.get(foo)).apply(field);
                    assertTrue(result.isPresent());
                    assertEquals(fieldValue, result.get().getValue());
                });
    }


    @Test
    public void applyReturnsEmptyOnMissingFields() {
        usecase = GetFieldValueByName.DEFAULT;

        Foo foo = new Foo("foo", 123L);

        Maybe<FieldTuple> result = usecase.apply(foo, "missing_Field");
        assertFalse(result.isPresent());
        assertFalse(result.exists());
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
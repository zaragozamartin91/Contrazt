package io.github.zaragozamartin91.contrazt.main;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GetAllFieldNamesTest {

    private final GetAllFieldNames getAllFieldNames = GetAllFieldNames.DEFAULT;

    @Test
    void getFieldNames() {
        List<String> fieldNames = getAllFieldNames.apply(new Nested1("pepe", 1L, 2));
        HashSet<String> expected = new HashSet<>(Arrays.asList("foo", "bar.baz", "bar.bash.zort"));
        assertTrue(fieldNames.containsAll(expected));
        assertTrue(expected.containsAll(fieldNames));
    }

    @Test
    void getFieldNamesFromEmptyClassReturnsEmptyList() {
        List<String> fieldNames = getAllFieldNames.apply(new EmptyClass());
        System.out.println(fieldNames);
        assertTrue(fieldNames.isEmpty());
    }

    @Test
    void getFieldNamesOfObjectTypeReturnsEmptyList() {
        assertTrue(getAllFieldNames.apply(Object.class).isEmpty());
    }

    @Test
    void getFieldNamesFindsNestedFields() {
        List<String> fieldNames = getAllFieldNames.apply(Child.class);
        HashSet<String> expected = new HashSet<>(Arrays.asList("childField", "nestedChild.nestedChildField", "parentField"));
        assertTrue(new HashSet<>(fieldNames).containsAll(expected));
        assertTrue(expected.containsAll(new HashSet<>(fieldNames)));
    }

    static class EmptyClass {

    }

    static class Parent {
        private String parentField;
    }

    static class Child extends Parent {
        private long childField;
        private NestedChild nestedChild;

        static class NestedChild {
            private int nestedChildField;
        }
    }

    static class Nested1 {
        private String foo;
        private Nested2 bar;

        Nested1(String f, long b, int z) {
            foo = f;
            bar = new Nested2();
            bar.baz = b;
            bar.bash = new Nested2.Nested3();
            bar.bash.zort = z;
        }

        static class Nested2 {
            private long baz;
            private Nested3 bash;

            static class Nested3 {
                private int zort;
            }
        }
    }
}
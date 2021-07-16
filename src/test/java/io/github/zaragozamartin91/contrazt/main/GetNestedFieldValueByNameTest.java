package io.github.zaragozamartin91.contrazt.main;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GetNestedFieldValueByNameTest {
    private GetNestedFieldValueByName usecase;

    @Test
    void applyReturnsMatchingNestedFieldValue() {
        usecase = GetNestedFieldValueByName.DEFAULT;

        Nested1 nested1 = new Nested1("one", 2L, 3);

        Maybe<FieldTuple> result = usecase.apply(nested1, "bar.bash.zort");
        assertTrue(result.isPresent());
        assertEquals(3, result.get().getValue());
    }

    @Test
    void applyReturnsEmptyOnMissingField() {
        usecase = GetNestedFieldValueByName.DEFAULT;

        Nested1 nested1 = new Nested1("one", 2L, 3);

        Maybe<FieldTuple> result = usecase.apply(nested1, "bar.missing.zort");
        assertFalse(result.isPresent());
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
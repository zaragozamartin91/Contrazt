package io.github.zaragozamartin91.contrazt.main;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SingleContraztTest {

    @Test
    void getValue() {
        Nested1 obj = new Nested1("Hello", 9L, 1);
        SingleContrazt singleContrazt = new SingleContrazt(obj);
        Maybe<FieldTuple> dotted = singleContrazt.getValue("bar.bash.zort");
        Maybe<FieldTuple> chained = singleContrazt.getValue("bar", "bash", "zort");
        Maybe<FieldTuple> mixed = singleContrazt.getValue("bar.bash", "zort");

        assertTrue(dotted.exists());
        assertTrue(chained.exists());
        assertTrue(mixed.exists());
        assertEquals(dotted, chained);
        assertEquals(dotted, mixed);
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
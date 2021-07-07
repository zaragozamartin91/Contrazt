package io.github.zaragozamartin91.contrazt.usecase;

import org.junit.jupiter.api.Test;

import java.util.List;

class GetAllFieldNamesTest {

    @Test
    void getFieldNames() {
        GetAllFieldNames getAllFieldNames = new GetAllFieldNames(false, true, true);
        List<String> fieldNames = getAllFieldNames.apply(new Nested1("pepe", 1L, 2));

        System.out.println(fieldNames);
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
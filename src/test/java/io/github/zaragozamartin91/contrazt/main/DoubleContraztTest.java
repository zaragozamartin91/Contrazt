package io.github.zaragozamartin91.contrazt.main;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class DoubleContraztTest {

    // TODO : add more tests

    @ParameterizedTest
    @CsvSource(
            delimiter = ',',
            value = {
                    "foo,2,3,bar,4,5,VALUE_MISMATCH,VALUE_MISMATCH,VALUE_MISMATCH",
                    "foo,2,3,foo,4,5,EQUAL,VALUE_MISMATCH,VALUE_MISMATCH",
            })
    void compareAllFieldsOfSameObjectType(
            String nstr1, long nlong1, int nint1,
            String nstr2, long nlong2, int nint2,
            String result1, String result2, String result3
    ) {
        Nested1 n1 = new Nested1(nstr1, nlong1, nint1);
        Nested1 n2 = new Nested1(nstr2, nlong2, nint2);
        List<FieldDiff> fieldDiff = Contrazt.of(n1).and(n2).compareAllFields();
        List<FieldDiffStatus> results = Arrays.asList(
                FieldDiffStatus.valueOf(result1),
                FieldDiffStatus.valueOf(result2),
                FieldDiffStatus.valueOf(result3));
        AtomicInteger idx = new AtomicInteger();
        assertAll(
                () -> assertEquals(results.get(idx.get()), fieldDiff.get(idx.getAndIncrement()).getResult()),
                () -> assertEquals(results.get(idx.get()), fieldDiff.get(idx.getAndIncrement()).getResult()),
                () -> assertEquals(results.get(idx.get()), fieldDiff.get(idx.getAndIncrement()).getResult())
        );
    }

    @Test
    void footest() throws NoSuchFieldException {
        Field df = Foobar.class.getDeclaredField("STATIC_FIELD");
        assertTrue(Modifier.isStatic(df.getModifiers()));
    }

    static class Foobar {
        private static int STATIC_FIELD;
    }

    static class Nested1 {
        private String nstr;
        private Nested2 nested2;

        Nested1(String f, long b, int z) {
            nstr = f;
            nested2 = new Nested2();
            nested2.nlong = b;
            nested2.nested3 = new Nested2.Nested3();
            nested2.nested3.nint = z;
        }

        static class Nested2 {
            private long nlong;
            private Nested3 nested3;

            static class Nested3 {
                private int nint;
            }
        }
    }
}
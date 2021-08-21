package io.github.zaragozamartin91.contrazt.main;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.github.zaragozamartin91.contrazt.main.FieldDiffStatus.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DoubleContraztTest {

    // TODO : add more tests

    @Test
    void matchProperties() {
        // TODO : add tests for scenario of similar types (like a Long and an Int)

        // GIVEN
        String propertyName = "someProperty";
        List<Object[]> containerPropertyTuples = Arrays.asList(
                new Object[]{null, new ContainerPropertyStub(propertyName, "value"), MISSING_FIRST},
                new Object[]{new ContainerPropertyStub(propertyName, "value"), null, MISSING_SECOND},
                new Object[]{new ContainerPropertyStub(propertyName, null), new ContainerPropertyStub(propertyName, null), EQUALS_NULL},
                new Object[]{new ContainerPropertyStub(propertyName, null), new ContainerPropertyStub(propertyName, "value"), NULL_FIRST},
                new Object[]{new ContainerPropertyStub(propertyName, "value"), new ContainerPropertyStub(propertyName, null), NULL_SECOND},
                new Object[]{new ContainerPropertyStub(propertyName, "value"), new ContainerPropertyStub(propertyName, "value"), EQUAL},
                new Object[]{new ContainerPropertyStub(propertyName, "value"), new ContainerPropertyStub(propertyName, "other_value"), VALUE_MISMATCH},
                new Object[]{new ContainerPropertyStub(propertyName, "value"), new ContainerPropertyStub(propertyName, 2), TYPE_MISMATCH}
        );

        containerPropertyTuples.forEach(tuple -> {
            ContainerProperty firstProperty = (ContainerProperty) tuple[0];
            ContainerProperty secondProperty = (ContainerProperty) tuple[1];
            FieldDiffStatus expectedStatus = (FieldDiffStatus) tuple[2];

            System.out.printf("Testing %s with %s ; expected status = %s%n", firstProperty, secondProperty, expectedStatus);

            Class<?> firstType = Optional.ofNullable(firstProperty).flatMap(ContainerProperty::getValueType).orElse(null);
            Object firstValue = Optional.ofNullable(firstProperty).flatMap(ContainerProperty::getValue).orElse(null);

            Class<?> secondType = Optional.ofNullable(secondProperty).flatMap(ContainerProperty::getValueType).orElse(null);
            Object secondValue = Optional.ofNullable(secondProperty).flatMap(ContainerProperty::getValue).orElse(null);

            // WHEN
            FieldDiff result = DoubleContrazt.matchProperties(propertyName, firstProperty, secondProperty);

            // THEN
            assertEquals(
                    new FieldDiff(propertyName, firstType, firstValue, secondType, secondValue, expectedStatus),
                    result
            );
        });
    }

    @Test
    void contrastAllFields() {
        // WHEN
        One one = new One("one", 2L, 3);
        Two two = new Two(2L, 4, BigDecimal.TEN);
        DoubleContrazt contrazt = Contrazt.of(one).and(two);

        // GIVEN
        List<FieldDiff> result = contrazt.contrastAllFields();

        // THEN
        List<Object[]> expected = Arrays.asList(
            new Object[] {"foo" , MISSING_SECOND},
            new Object[] {"bar" , EQUAL},
            new Object[] {"baz" , VALUE_MISMATCH},
            new Object[] {"bash" , MISSING_FIRST}
        );
        Map<String, FieldDiffStatus> expectedMap =
                expected.stream().collect(Collectors.toMap(obj -> obj[0].toString(), obj -> (FieldDiffStatus) obj[1]));
        Map<String, FieldDiffStatus> resultMap = result.stream().collect(Collectors.toMap(FieldDiff::getFieldName, FieldDiff::getResult));
        assertEquals(expectedMap, resultMap);
    }

    static class One {
        private final String foo;
        private final long bar;
        private final int baz;

        One(String foo, long bar, int baz) {
            this.foo = foo;
            this.bar = bar;
            this.baz = baz;
        }
    }

    static class Two {
        private final long bar;
        private final int baz;
        private final BigDecimal bash;

        Two(long bar, int baz, BigDecimal bash) {
            this.bar = bar;
            this.baz = baz;
            this.bash = bash;
        }
    }

    @Test
    void testIsStatic() throws NoSuchFieldException {
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
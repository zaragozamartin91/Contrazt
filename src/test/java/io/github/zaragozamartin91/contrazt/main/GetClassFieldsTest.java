package io.github.zaragozamartin91.contrazt.main;

import io.github.zaragozamartin91.contrazt.main.pojo.PojoWithAtomProperties;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class GetClassFieldsTest {
    private final GetClassFields getClassFields = GetClassFields.DEFAULT;

    @Test
    void apply() {
        // GIVEN & WHEN
        List<Field> result = getClassFields.apply(PojoWithAtomProperties.class);

        // THEN
        Set<String> okNames = IntStream.rangeClosed(0, 8).mapToObj(i -> "field" + i).collect(Collectors.toSet());
        Set<String> resultNames = result.stream().map(Field::getName).collect(Collectors.toSet());
        assertTrue(resultNames.containsAll(okNames));
        assertTrue(okNames.containsAll(resultNames));
        assertFalse(resultNames.contains("STATIC_FIELD"));
    }
}
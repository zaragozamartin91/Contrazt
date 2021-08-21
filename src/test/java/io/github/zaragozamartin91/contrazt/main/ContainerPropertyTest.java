package io.github.zaragozamartin91.contrazt.main;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContainerPropertyTest {

    @Test
    void isAtom() {
        assertTrue(new CollectionProperty(Collections.singletonList("someValue"), 0, "MAIN").isAtom());
        assertTrue(new CollectionProperty(Collections.singletonList(12), 0, "MAIN").isAtom());
        assertTrue(new CollectionProperty(Collections.singletonList(13L), 0, "MAIN").isAtom());
        assertTrue(new CollectionProperty(Collections.singletonList(true), 0, "MAIN").isAtom());
        assertTrue(new CollectionProperty(Collections.singletonList(14.5), 0, "MAIN").isAtom());
        assertTrue(new CollectionProperty(Collections.singletonList(87.2f), 0, "MAIN").isAtom());
        assertTrue(new CollectionProperty(Collections.singletonList(null), 0, "MAIN").isAtom());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "one.two{three}.four[0].five,one.two.three.four[0].five",
            "one.two{three}.four[0].five{six},one.two.three.four[0].five.six"},
            delimiter = ',')
    void normalizeName(String input, String expected) {
        Object value = "some_Value";
        ContainerPropertyStub containerPropertyStub = new ContainerPropertyStub(input, value);
        assertEquals(expected, containerPropertyStub.normalizeName());
    }
}
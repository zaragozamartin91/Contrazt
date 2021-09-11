package io.github.zaragozamartin91.contrazt.main;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LoadAtomTypesFromResourceFileTest {


    @Test
    void getShouldReturnValidTypesSpecifiedInStandardFile() {
        LoadAtomTypesFromResourceFile usecase = new LoadAtomTypesFromResourceFile("atomic-types_VALID.txt");
        Set<Class<?>> types = usecase.get();
        assertEquals(2, types.size());
    }

    @Test
    void getShouldThrowIllegalStateExceptionOnInvalidTypes() {
        LoadAtomTypesFromResourceFile usecase = new LoadAtomTypesFromResourceFile("atomic-types_INVALID.txt");
        assertThrows(IllegalStateException.class, usecase::get);
    }
}
package io.github.zaragozamartin91.contrazt.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LoadAtomTypesFromResourceFile implements Supplier<Set<Class<?>>> {
    private static final Logger logger = Logger.getLogger(LoadAtomTypesFromResourceFile.class.getName());

    private final String fileName;

    LoadAtomTypesFromResourceFile(String fileName) {
        this.fileName = fileName;
    }

    public LoadAtomTypesFromResourceFile() {
        this("atomic-types.txt");
    }

    @Override
    public Set<Class<?>> get() {
        InputStream atomicTypesFileStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
        if (atomicTypesFileStream == null) {
            logger.info("No atomic-types.txt file found... Using default atomic types");
            return new HashSet<>();
        } else {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(atomicTypesFileStream))) {
                Set<String> typeNames = readTypeNames(bufferedReader);
                return parseTypes(typeNames);
            } catch (IOException e) {
                logger.warning("Error while loading atomic-types.txt");
                throw new IllegalStateException("Error while loading atomic-types.txt", e);
            }
        }
    }

    private Set<String> readTypeNames(BufferedReader bufferedReader) throws IOException {
        Set<String> typeNames = new HashSet<>();
        for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
            typeNames.add(line.trim());
        }
        return typeNames.stream().filter(s -> !s.isEmpty()).collect(Collectors.toSet());
    }

    private Set<Class<?>> parseTypes(Set<String> typeNames) {
        Set<String> flawedTypes = new HashSet<>();
        Set<Class<?>> types = typeNames.stream().flatMap(s -> {
            try {
                return Stream.of(Class.forName(s));
            } catch (ClassNotFoundException e) {
                flawedTypes.add(s);
                return Stream.of();
            }
        }).collect(Collectors.toSet());

        if (flawedTypes.isEmpty()) {
            logger.info("Custom atomic types loaded: " + typeNames);
            return types;
        } else {
            throw new IllegalStateException("Types " + flawedTypes + " are invalid!");
        }
    }
}

package io.github.zaragozamartin91.usecase;

import java.util.Optional;
import java.util.function.Consumer;

public class ValidateFieldName implements Consumer<String> {
    @Override
    public void accept(String s) {
        Optional.ofNullable(s)
                .map(String::trim)
                .filter(ss -> !ss.isEmpty())
                .orElseThrow(() -> new IllegalArgumentException("Invalid field name"));
    }
}

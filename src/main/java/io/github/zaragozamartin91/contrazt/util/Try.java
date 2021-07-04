package io.github.zaragozamartin91.contrazt.util;

import java.util.function.Function;

public class Try {
     public static <T,R> Function<T,R> unchecked(CheckedFunction<T,R> fun) {
        return t -> {
            try {
                return fun.apply(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}

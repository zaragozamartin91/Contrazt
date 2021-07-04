package io.github.zaragozamartin91.util;

import java.util.function.Function;

public interface CheckedFunction<T,R> {
    R apply(T parameter) throws Exception;


}

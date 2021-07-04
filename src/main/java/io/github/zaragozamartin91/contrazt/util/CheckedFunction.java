package io.github.zaragozamartin91.contrazt.util;

public interface CheckedFunction<T,R> {
    R apply(T parameter) throws Exception;


}

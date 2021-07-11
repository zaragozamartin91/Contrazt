package io.github.zaragozamartin91.contrazt.main;

public interface CheckedFunction<T,R> {
    R apply(T parameter) throws Exception;


}

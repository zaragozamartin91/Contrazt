package io.github.zaragozamartin91.contrazt.main;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;

public class Maybe<T> {
    private final T value;
    private final boolean exists;

    private Maybe(T value, boolean exists) {
        this.value = value;
        this.exists = exists;
    }

    public static <T> Maybe<T> empty() {
        return new Maybe<>(null, false);
    }

    public static <T> Maybe<T> of(T value) {
        return new Maybe<>(value, true);
    }

    public Optional<T> toOptional() {
        return isPresent() ? Optional.ofNullable(value) : Optional.empty();
    }

    /**
     * Returns true if a value EXISTS
     *
     * @return true if a value is set (even null)
     */
    public boolean exists() {
        return exists;
    }

    /**
     * Returns true if a value EXISTS and is different from null.
     *
     * @return true if a value EXISTS and is different from null
     */
    public boolean isPresent() {
        return exists() && Optional.ofNullable(value).isPresent();
    }

    public <R> Maybe<R> flatMap(Function<T, Maybe<R>> fn) {
        return exists() ?
                fn.andThen(m -> new Maybe<>(m.value, m.exists)).apply(this.value) :
                Maybe.empty();
    }

    public <R> Maybe<R> map(Function<T, R> fn) {
        return this.flatMap(t -> Maybe.of(fn.apply(value)));
    }

    public T get() {
        return this.toOptional().orElseThrow(NoSuchElementException::new);
    }
}

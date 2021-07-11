package io.github.zaragozamartin91.contrazt.main;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

class Maybe<T> {
    private final T value;
    private final boolean exists;

    private Maybe(T value, boolean exists) {
        this.value = value;
        this.exists = exists;
    }

    static <T> Maybe<T> empty() {
        return new Maybe<>(null, false);
    }

    static <T> Maybe<T> of(T value) {
        return new Maybe<>(value, true);
    }

    Optional<T> toOptional() {
        return isPresent() ? Optional.ofNullable(value) : Optional.empty();
    }

    /**
     * Returns true if a value EXISTS
     *
     * @return true if a value is set (even null)
     */
    boolean exists() {
        return exists;
    }

    boolean missing() { return !exists(); }

    /**
     * Returns true if a value EXISTS and is different from null.
     *
     * @return true if a value EXISTS and is different from null
     */
    boolean isPresent() {
        return exists() && Optional.ofNullable(value).isPresent();
    }

    <R> Maybe<R> flatMap(Function<T, Maybe<R>> fn) {
        return exists() ?
                fn.andThen(m -> new Maybe<>(m.value, m.exists)).apply(this.value) :
                Maybe.empty();
    }

    <R> Maybe<R> map(Function<T, R> fn) {
        return this.flatMap(t -> Maybe.of(fn.apply(value)));
    }

    T get() {
        return this.toOptional().orElseThrow(NoSuchElementException::new);
    }

    T orNull() {
        return this.toOptional().orElse(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Maybe<?> other = (Maybe<?>) o;
        return this.exists == other.exists && Objects.equals(this.value, other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, exists);
    }
}

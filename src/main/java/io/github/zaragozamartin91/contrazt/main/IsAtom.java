package io.github.zaragozamartin91.contrazt.main;

import java.util.function.Predicate;

/**
 * Checks if a type is an ATOM , that is to say, holds no relevant fields to be inspected
 */
class IsAtom implements Predicate<Class<?>> {

    @Override
    public boolean test(Class<?> currType) {
        return currType.isPrimitive()
                || isWrapperType(currType)
                || isAtomicType(currType)
                || currType.isSynthetic();
    }

    private boolean isWrapperType(Class<?> type) {
        return AtomType.WRAPPERS.stream().anyMatch(s -> s.isAssignableFrom(type));
    }

    private boolean isAtomicType(Class<?> type) {
        return AtomType.ATOMS.stream().anyMatch(s -> s.isAssignableFrom(type));
    }
}

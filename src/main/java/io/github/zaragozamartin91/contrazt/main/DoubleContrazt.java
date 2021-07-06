package io.github.zaragozamartin91.contrazt.main;

public class DoubleContrazt {
    private final Object _first;
    private final Object _second;

    public DoubleContrazt(Object first, Object second) {
        this._first = first;
        this._second = second;
    }

    public SingleContrazt first() {
        return new SingleContrazt(_first);
    }

    public SingleContrazt second() {
        return new SingleContrazt(_second);
    }

    public DoubleContrazt reverse() {
        return new DoubleContrazt(_first, _second);
    }

    
}

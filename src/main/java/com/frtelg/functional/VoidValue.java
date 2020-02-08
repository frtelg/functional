package com.frtelg.functional;

@SuppressWarnings("WeakerAccess")
public class VoidValue {
    public static VoidValue result() {
        return new VoidValue();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof VoidValue;
    }
}
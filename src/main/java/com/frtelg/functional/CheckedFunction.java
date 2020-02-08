package com.frtelg.functional;

@FunctionalInterface
public interface CheckedFunction<I, O> {
    O apply(I i);
}

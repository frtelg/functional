package com.frtelg.functional;

public interface CheckedSupplier<T> {
    T get() throws Throwable;
}

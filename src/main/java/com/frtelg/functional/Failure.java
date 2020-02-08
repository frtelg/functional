package com.frtelg.functional;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("WeakerAccess")
public class Failure<T> extends Try<T> {
    private final Throwable throwable;

    public Failure(Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public T getSuccessOrElse(T that) {
        Objects.requireNonNull(that);
        return that;
    }

    @Override
    public T getSuccessOrElseGet(Supplier<T> that) {
        Objects.requireNonNull(that);
        return that.get();
    }

    @Override
    public T getSuccessOrElseGet(Function<Throwable, ? extends T> that) {
        Objects.requireNonNull(that);
        return that.apply(throwable);
    }

    @Override
    public <Y> Try<Y> map(CheckedFunction<? super T, ? extends Y> function) {
        Objects.requireNonNull(function);
        return Try.failure(throwable);
    }

    @Override
    public <Y> Try<Y> flatMap(CheckedFunction<? super T, Try<Y>> function) {
        Objects.requireNonNull(function);
        return Try.failure(throwable);
    }

    @Override
    public Optional<T> toOptional() {
        return Optional.empty();
    }

    @Override
    public Failure<T> getFailure() throws IllegalStateException {
        return this;
    }

    @Override
    public Success<T> getSuccess() throws IllegalStateException {
        throw new IllegalStateException("getSuccess on a failed Try");
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void throwException() throws Throwable {
        throw this.throwable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Failure<?> failure = (Failure<?>) o;

        return throwable.equals(failure.throwable);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        return 31 * result + throwable.hashCode();
    }
}

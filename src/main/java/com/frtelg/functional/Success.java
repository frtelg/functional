package com.frtelg.functional;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("WeakerAccess")
public class Success<T> extends Try<T> {
    private final T returnValue;

    Success(T returnValue) {
        this.returnValue = returnValue;
    }

    public T getReturnValue() {
        return returnValue;
    }

    @Override
    public boolean isSuccess() {
        return true;
    }

    @Override
    public T getSuccessOrElse(T that) {
        Objects.requireNonNull(that);
        return returnValue;
    }

    @Override
    public T getSuccessOrElseGet(Supplier<T> that) {
        Objects.requireNonNull(that);
        return returnValue;
    }

    @Override
    public T getSuccessOrElseGet(Function<Throwable, ? extends T> that) {
        Objects.requireNonNull(that);
        return returnValue;
    }

    @Override
    public void ifSuccessOrElse(Consumer<T> ifSuccess, Consumer<Throwable> ifFailure) {
        ifSuccess.accept(returnValue);
    }

    @Override
    public <Y> Try<Y> map(CheckedFunction<? super T, ? extends Y> function) {
        Objects.requireNonNull(function);
        return Try.execute(() -> function.apply(returnValue));
    }

    @Override
    public <Y> Try<Y> flatMap(CheckedFunction<? super T, Try<Y>> function) {
        Objects.requireNonNull(function);
        return function.apply(returnValue);
    }

    @Override
    public Optional<T> toOptional() {
        return Optional.of(returnValue);
    }

    @Override
    public Failure<T> getFailure() throws IllegalStateException {
        throw new IllegalStateException("getFailure on a successful Try");
    }

    @Override
    public Success<T> getSuccess() throws IllegalStateException {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Success<?> success = (Success<?>) o;

        return returnValue.equals(success.returnValue);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();

        return 31 * result + returnValue.hashCode();
    }
}

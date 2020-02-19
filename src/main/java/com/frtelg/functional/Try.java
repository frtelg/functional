package com.frtelg.functional;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("WeakerAccess")
public abstract class Try<T> {
    public static <R> Try<R> execute(CheckedSupplier<R> function) {
        try {
            return success(function.get());
        } catch (Throwable t) {
            return failure(t);
        }
    }

    public static Try<VoidValue> execute(CheckedRunnable function) {
        try {
            function.run();
        } catch (Throwable t) {
            return failure(t);
        }

        return success(VoidValue.result());
    }

    protected static <R> Success<R> success(R resultValue) {
        Objects.requireNonNull(resultValue);
        return new Success<>(resultValue);
    }

    protected static <R> Failure<R> failure(Throwable throwable) {
        Objects.requireNonNull(throwable);
        return new Failure<>(throwable);
    }

    public abstract boolean isSuccess();

    public boolean isFailure() {
        return !isSuccess();
    }

    public abstract T getSuccessOrElse(T that);

    public abstract T getSuccessOrElseGet(Supplier<T> that);

    public abstract T getSuccessOrElseGet(Function<Throwable, ? extends T> that);

    public abstract void ifSuccessOrElse(Consumer<T> ifSuccess, Consumer<Throwable> ifFailure);

    public abstract <Y> Try<Y> map(CheckedFunction<? super T, ? extends Y> function);

    public abstract <Y> Try<Y> flatMap(CheckedFunction<? super T, Try<Y>> function);

    public abstract Optional<T> toOptional();

    public abstract Failure<T> getFailure() throws IllegalStateException;

    public abstract Success<T> getSuccess() throws IllegalStateException;
}

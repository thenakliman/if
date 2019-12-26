package com.thenakliman.ifs;

import java.util.function.Function;
import java.util.function.Supplier;


final class NullTernary {

    interface IElse<T, R> {
        R elseValue(R value);

        R elseMap(Function<T, R> function);

        <X extends Throwable> R elseThrow(Supplier<? extends X> exceptionSupplier) throws X;
    }

    interface IExceptionHandler<T> {
        <U> U elseValue(U returnValue);

        <U> U elseMap(Function<T, U> function);

        <X extends Throwable> void elseThrow(Supplier<? extends X> exceptionSupplier) throws X;
    }

    interface IThen<T> {
        <R> IElse<T, R> thenValue(R returnValue);

        <R> IElse<T, R> thenGet(Supplier<R> supplier);

        <X extends Throwable> IExceptionHandler<T> thenThrow(Supplier<? extends X> exceptionSupplier) throws X;
    }

    static final class NullThen<T> implements IThen<T> {
        public <R> IElse<T, R> thenValue(R returnValue) {
            return new NullElse<>(returnValue);
        }

        public <R> IElse<T, R> thenGet(Supplier<R> supplier) {
            final R returnValue = supplier.get();
            return new NullElse<>(returnValue);
        }

        public <X extends Throwable> IExceptionHandler<T> thenThrow(Supplier<? extends X> exceptionSupplier) throws X {
            throw exceptionSupplier.get();
        }
    }

    static final class NonNullThen<T> implements IThen<T> {
        private final T object;

        NonNullThen(T object) {
            this.object = object;
        }

        public <R> IElse<T, R> thenValue(R returnValue) {
            return new NonNullElse<>(this.object);
        }

        public <R> IElse<T, R> thenGet(Supplier<R> supplier) {
            return new NonNullElse<>(this.object);
        }

        public <X extends Throwable> IExceptionHandler<T> thenThrow(Supplier<? extends X> exceptionSupplier) throws X {
            return new NonNullExceptionHandler<>(this.object);
        }
    }

    static final class NonNullExceptionHandler<T> implements IExceptionHandler<T> {
        private final T object;

        NonNullExceptionHandler(T object) {
            this.object = object;
        }

        @Override
        public <U> U elseValue(U returnValue) {
            return returnValue;
        }

        @Override
        public <U> U elseMap(Function<T, U> function) {
            return function.apply(this.object);
        }

        public <X extends Throwable> void elseThrow(Supplier<? extends X> exceptionSupplier) throws X {
            throw exceptionSupplier.get();
        }
    }

    static final class NullElse<T, R> implements IElse<T, R> {
        private final R returnValue;

        NullElse(R returnValue) {
            this.returnValue = returnValue;
        }

        @Override
        public R elseValue(R value) {
            return this.returnValue;
        }

        @Override
        public R elseMap(Function<T, R> function) {
            return this.returnValue;
        }

        public <X extends Throwable> R elseThrow(Supplier<? extends X> exceptionSupplier) throws X {
            return this.returnValue;
        }
    }

    static final class NonNullElse<T, R> implements IElse<T, R> {
        private final T object;

        NonNullElse(T object) {
            this.object = object;
        }

        @Override
        public R elseValue(R returnValue) {
            return returnValue;
        }

        @Override
        public R elseMap(Function<T, R> function) {
            return function.apply(this.object);
        }

        public <X extends Throwable> R elseThrow(Supplier<? extends X> exceptionSupplier) throws X {
            throw exceptionSupplier.get();
        }
    }
}

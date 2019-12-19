package com.thenakliman.ifs;

import java.util.function.Function;
import java.util.function.Supplier;


final class NullTernary {

    interface IElse<T, R> {
        R elseReturn(R value);

        R elseMap(Function<T, R> function);

        <X extends Throwable> R elseThrow(Supplier<? extends X> exceptionSupplier) throws X;
    }

    interface IExceptionHandler<T> {
        <U> U elseReturn(U returnValue);

        <U> U elseMap(Function<T, U> function);

        <X extends Throwable> void elseThrow(Supplier<? extends X> exceptionSupplier) throws X;
    }

    interface IThen<T> {
        <R> IElse<T, R> thenReturn(R returnValue);

        <R> IElse<T, R> thenGet(Supplier<R> supplier);

        <X extends Throwable> IExceptionHandler<T> thenThrow(Supplier<? extends X> exceptionSupplier) throws X;
    }

    static final class NullThen<T> implements IThen<T> {
        public <R> IElse<T, R> thenReturn(R returnValue) {
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

        public <R> IElse<T, R> thenReturn(R returnValue) {
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
        public <U> U elseReturn(U returnValue) {
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
        public R elseReturn(R value) {
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
        public R elseReturn(R returnValue) {
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

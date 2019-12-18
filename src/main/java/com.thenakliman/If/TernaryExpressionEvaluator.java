package com.thenakliman.If;

import java.util.function.Supplier;

class TernaryExpressionEvaluator {
    public interface IProcedure {
        void call();
    }

    interface IElse<T> {
        T elseGet(Supplier<T> supplier);

        <X extends Throwable> T elseThrow(Supplier<? extends X> exceptionSupplier) throws X;
    }

    interface IThenGet {
        <T> IElse<T> thenGet(Supplier<T> supplier);

        IElseCall thenCall(IProcedure procedure);

        <X extends Throwable> IExceptionHandler thenThrow(Supplier<? extends X> exceptionSupplier) throws X;
    }

    interface IElseCall {
        void elseCall(IProcedure procedure);

        <X extends Throwable> void elseThrow(Supplier<? extends X> exceptionSupplier) throws X;
    }

    interface IExceptionHandler {
        void elseCall(IProcedure procedure);

        <X extends Throwable> IExceptionHandler thenThrow(Supplier<? extends X> exceptionSupplier) throws X;

        <T> T elseGet(Supplier<T> supplier);
    }


    static class TrueThen implements IThenGet {
        @Override
        public <T> IElse<T> thenGet(Supplier<T> supplier) {
            return new TrueElseGet<>(supplier.get());
        }

        @Override
        public IElseCall thenCall(IProcedure procedure) {
            procedure.call();
            return new TrueElseCall();
        }

        public <X extends Throwable> IExceptionHandler thenThrow(Supplier<? extends X> exceptionSupplier) throws X {
            throw exceptionSupplier.get();
        }
    }

    static class FalseThen implements IThenGet {
        @Override
        public <T> IElse<T> thenGet(Supplier<T> supplier) {
            return new FalseElseGet<>();
        }

        @Override
        public IElseCall thenCall(IProcedure procedure) {
            return new FalseElseCall();
        }

        public <X extends Throwable> IExceptionHandler thenThrow(Supplier<? extends X> exceptionSupplier) throws X {
            return new ElseExceptionHanlder();
        }
    }

    private static class ElseExceptionHanlder implements IExceptionHandler {
        @Override
        public void elseCall(IProcedure procedure) {
            procedure.call();
        }

        @Override
        public <X extends Throwable> IExceptionHandler thenThrow(Supplier<? extends X> exceptionSupplier) throws X {
            throw exceptionSupplier.get();
        }

        @Override
        public <T> T elseGet(Supplier<T> supplier) {
            return supplier.get();
        }
    }


    static class TrueElseGet<T> implements IElse<T> {
        private T value;

        TrueElseGet(T value) {
            this.value = value;
        }

        @Override
        public T elseGet(Supplier<T> supplier) {
            return this.value;
        }

        @Override
        public <X extends Throwable> T elseThrow(Supplier<? extends X> exceptionSupplier) throws X {
            return this.value;
        }
    }

    static class FalseElseGet<T> implements IElse<T> {
        @Override
        public T elseGet(Supplier<T> supplier) {
            return supplier.get();
        }

        @Override
        public <X extends Throwable> T elseThrow(Supplier<? extends X> exceptionSupplier) throws X {
            throw exceptionSupplier.get();
        }
    }

    static class TrueElseCall implements IElseCall {
        @Override
        public void elseCall(IProcedure procedure) {
            // does not anything
        }

        @Override
        public <X extends Throwable> void elseThrow(Supplier<? extends X> exceptionSupplier) throws X {
            // does not anything
        }
    }

    static class FalseElseCall implements IElseCall {
        @Override
        public void elseCall(IProcedure procedure) {
            procedure.call();
        }

        @Override
        public <X extends Throwable> void elseThrow(Supplier<? extends X> exceptionSupplier) throws X {
            throw exceptionSupplier.get();
        }
    }
}

package com.thenakliman.ifs;

import java.util.function.Supplier;

class IfExpression {
    public interface IProcedure {
        void call();
    }

    interface IElseGet<T> {
        T elseGet(Supplier<T> supplier);

        T elseValue(T value);

        <X extends Throwable> T elseThrow(Supplier<? extends X> exceptionSupplier) throws X;
    }

    interface IElseCall {
        void elseCall(IProcedure procedure);

        <X extends Throwable> void elseThrow(Supplier<? extends X> exceptionSupplier) throws X;
    }

    interface IElseValue<T> {
        T elseGet(Supplier<T> supplier);

        T elseValue(T value);

        <X extends Throwable> T elseThrow(Supplier<? extends X> exceptionSupplier) throws X;
    }

    interface IExpressionThen {
        <T> IElseGet<T> thenGet(Supplier<T> supplier);

        <T> IElseValue<T> thenValue(T supplier);

        IElseCall thenCall(IProcedure procedure);

        <X extends Throwable> IExceptionElse thenThrow(Supplier<? extends X> exceptionSupplier) throws X;
    }

    interface IExceptionElse {
        void elseCall(IProcedure procedure);

        <T> T elseValue(T value);

        <X extends Throwable> void elseThrow(Supplier<? extends X> exceptionSupplier) throws X;

        <T> T elseGet(Supplier<T> supplier);
    }


    static class TrueExpressionThen implements IExpressionThen {
        @Override
        public <T> IElseGet<T> thenGet(Supplier<T> supplier) {
            return new TrueExpressionGet<>(supplier.get());
        }

        @Override
        public <T> IElseValue<T> thenValue(T value) {
            return new TrueElseValue<T>(value);
        }

        @Override
        public IElseCall thenCall(IProcedure procedure) {
            procedure.call();
            return new TrueExpressionCall();
        }

        public <X extends Throwable> IExceptionElse thenThrow(Supplier<? extends X> exceptionSupplier) throws X {
            throw exceptionSupplier.get();
        }

        private static class TrueElseValue<T> implements IElseValue<T> {
            final private T value;
            public TrueElseValue(T value) {
                this.value = value;
            }

            @Override
            public T elseGet(Supplier<T> T) {
                return this.value;
            }

            @Override
            public T elseValue(T value) {
                return this.value;
            }

            @Override
            public <X extends Throwable> T elseThrow(Supplier<? extends X> exceptionSupplier) throws X {
                return this.value;
            }
        }
    }

    static class FalseExpressionThen implements IExpressionThen {
        @Override
        public <T> IElseGet<T> thenGet(Supplier<T> supplier) {
            return new FalseExpressionGet<>();
        }

        @Override
        public <T> IElseValue<T> thenValue(T supplier) {
            return new FalseElseValue<T>();
        }

        @Override
        public IElseCall thenCall(IProcedure procedure) {
            return new FalseExpressionCall();
        }

        public <X extends Throwable> IExceptionElse thenThrow(Supplier<? extends X> exceptionSupplier) throws X {
            return new ElseException();
        }

        private static class FalseElseValue<T> implements IElseValue<T> {
            @Override
            public T elseGet(Supplier<T> supplier) {
                return supplier.get();
            }

            @Override
            public T elseValue(T value) {
                return value;
            }

            public <X extends Throwable> T elseThrow(Supplier<? extends X> exceptionSupplier) throws X {
                throw exceptionSupplier.get();
            }
        }
    }

    private static class ElseException implements IExceptionElse {
        @Override
        public void elseCall(IProcedure procedure) {
            procedure.call();
        }

        @Override
        public <T> T elseValue(T value) {
            return value;
        }

        @Override
        public <X extends Throwable> void elseThrow(Supplier<? extends X> exceptionSupplier) throws X {
            throw exceptionSupplier.get();
        }

        @Override
        public <T> T elseGet(Supplier<T> supplier) {
            return supplier.get();
        }
    }


    static class TrueExpressionGet<T> implements IElseGet<T> {
        final private T value;

        TrueExpressionGet(T value) {
            this.value = value;
        }

        @Override
        public T elseGet(Supplier<T> supplier) {
            return this.value;
        }

        @Override
        public T elseValue(T value) {
            return value;
        }

        @Override
        public <X extends Throwable> T elseThrow(Supplier<? extends X> exceptionSupplier) throws X {
            return this.value;
        }
    }

    static class FalseExpressionGet<T> implements IElseGet<T> {
        @Override
        public T elseGet(Supplier<T> supplier) {
            return supplier.get();
        }

        @Override
        public T elseValue(T value) {
            return value;
        }

        @Override
        public <X extends Throwable> T elseThrow(Supplier<? extends X> exceptionSupplier) throws X {
            throw exceptionSupplier.get();
        }
    }

    static class TrueExpressionCall implements IElseCall {
        @Override
        public void elseCall(IProcedure procedure) {
            // does not anything
        }

        @Override
        public <X extends Throwable> void elseThrow(Supplier<? extends X> exceptionSupplier) throws X {
            // does not anything
        }
    }

    static class FalseExpressionCall implements IElseCall {
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

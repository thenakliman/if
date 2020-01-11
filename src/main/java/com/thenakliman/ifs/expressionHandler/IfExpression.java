package com.thenakliman.ifs.expressionHandler;

import java.util.function.Supplier;

public class IfExpression {
    public interface IProcedure {
        void call();
    }

    public interface IElseIf<T> {
        IElse<T> thenGet(final Supplier<T> supplier);

        IElse<T> thenValue(final T value);

        <X extends Throwable> IElse<T> thenThrow(final Supplier<? extends X> exceptionSupplier) throws X;
    }

    public interface IIExceptionElseIf  {
        <T> T elseGet(final Supplier<T> supplier);

        <T> T elseValue(final T value);

        IExceptionElseIf elseIf(final boolean expression);

        <X extends Throwable> void elseThrow(final Supplier<? extends X> exceptionSupplier) throws X;
    }

    public interface IExceptionElseIf {
        <T> IElse<T> thenGet(final Supplier<T> supplier);

        <T> IElse<T> thenValue(final T value);

        <X extends Throwable> IIExceptionElseIf thenThrow(final Supplier<? extends X> exceptionSupplier) throws X;
    }

    public interface IElse<T> {
        T elseGet(final Supplier<T> supplier);

        T elseValue(final T value);

        IElseIf<T> elseIf(final boolean expression);

        <X extends Throwable> T elseThrow(final Supplier<? extends X> exceptionSupplier) throws X;
    }

    public interface IElseCall {
        void elseCall(final IProcedure procedure);

        <X extends Throwable> void elseThrow(final Supplier<? extends X> exceptionSupplier) throws X;
    }

    public interface IExpressionThen {
        <T> IElse<T> thenGet(final Supplier<T> supplier);

        <T> IElse<T> thenValue(final T supplier);

        IElseCall thenCall(final IProcedure procedure);

        <X extends Throwable> IExceptionElse thenThrow(final Supplier<? extends X> exceptionSupplier) throws X;
    }

    public interface IExceptionElse {
        void elseCall(final IProcedure procedure);

        <T> T elseValue(final T value);

        <X extends Throwable> void elseThrow(final Supplier<? extends X> exceptionSupplier) throws X;

        <T> T elseGet(final Supplier<T> supplier);

        IExceptionElseIf elseIf(final boolean expression);
    }

    public static class TrueExpressionThen implements IExpressionThen {
        @Override
        public <T> IElse<T> thenGet(final Supplier<T> supplier) {
            return new TrueExpression<>(supplier.get());
        }

        @Override
        public <T> IElse<T> thenValue(final T value) {
            return new TrueExpression<>(value);
        }

        @Override
        public IElseCall thenCall(final IProcedure procedure) {
            procedure.call();
            return new TrueExpressionCall();
        }

        public <X extends Throwable> IExceptionElse thenThrow(final Supplier<? extends X> exceptionSupplier) throws X {
            throw exceptionSupplier.get();
        }
    }

    public static class FalseExpressionThen implements IExpressionThen {
        @Override
        public <T> IElse<T> thenGet(final Supplier<T> supplier) {
            return new FalseExpression<>();
        }

        @Override
        public <T> IElse<T> thenValue(final T supplier) {
            return new FalseExpression<>();
        }

        @Override
        public IElseCall thenCall(final IProcedure procedure) {
            return new FalseExpressionCall();
        }

        public <X extends Throwable> IExceptionElse thenThrow(final Supplier<? extends X> exceptionSupplier) throws X {
            return new ElseException();
        }
    }

    private static class ElseException implements IExceptionElse {
        @Override
        public void elseCall(final IProcedure procedure) {
            procedure.call();
        }

        @Override
        public <T> T elseValue(final T value) {
            return value;
        }

        @Override
        public <X extends Throwable> void elseThrow(final Supplier<? extends X> exceptionSupplier) throws X {
            throw exceptionSupplier.get();
        }

        @Override
        public <T> T elseGet(final Supplier<T> supplier) {
            return supplier.get();
        }

        @Override
        public IExceptionElseIf elseIf(final boolean expression) {
            if (expression) {
                return new IExceptionTrueElseIfImpl();
            }

            return new IExceptionFalseElseIfImpl();
        }

        private static class IExceptionFalseElseIfImpl implements IExceptionElseIf {
            @Override
            public <T> IElse<T> thenGet(final Supplier<T> supplier) {
                return new FalseExpression<>();
            }

            @Override
            public <T> IElse<T> thenValue(final T value) {
                return new FalseExpression<>();
            }

            @Override
            public <X extends Throwable> IIExceptionElseIf thenThrow(final Supplier<? extends X> exceptionSupplier) throws X {
                return new IIExceptionFalseElseIfImpl();
            }

            private static class IIExceptionFalseElseIfImpl implements IIExceptionElseIf {
                @Override
                public <T> T elseGet(final Supplier<T> supplier) {
                    return supplier.get();
                }

                @Override
                public <T> T elseValue(final T value) {
                    return value;
                }

                @Override
                public IExceptionElseIf elseIf(final boolean expression) {
                    if (expression) {
                        return new IExceptionTrueElseIfImpl();
                    }

                    return new IExceptionFalseElseIfImpl();
                }

                @Override
                public <Y extends Throwable> void elseThrow(final Supplier<? extends Y> exceptionSupplier) throws Y {
                    throw exceptionSupplier.get();
                }
            }
        }

        private static class IExceptionTrueElseIfImpl implements IExceptionElseIf {
            @Override
            public <T> IElse<T> thenGet(Supplier<T> supplier) {
                return new TrueExpression<>(supplier.get());
            }

            @Override
            public <T> IElse<T> thenValue(T value) {
                return new TrueExpression<>(value);
            }

            @Override
            public <X extends Throwable> IIExceptionElseIf thenThrow(Supplier<? extends X> exceptionSupplier) throws X {
                throw exceptionSupplier.get();
            }
        }
    }

    private static class TrueExpression<T> implements IElse<T> {
        final private T value;

        TrueExpression(final T value) {
            this.value = value;
        }

        @Override
        public T elseGet(final Supplier<T> supplier) {
            return this.value;
        }

        @Override
        public T elseValue(final T elseValue) {
            return this.value;
        }

        @Override
        public IElseIf<T> elseIf(final boolean expression) {
            return new ElseIf<>(this.value);
        }

        @Override
        public <X extends Throwable> T elseThrow(final Supplier<? extends X> exceptionSupplier) throws X {
            return this.value;
        }

        private static class ElseIf<T> implements IElseIf<T> {
            final private T value;

            public ElseIf(final T value) {
                this.value = value;
            }

            @Override
            public IElse<T> thenGet(final Supplier<T> supplier) {
                return new TrueExpression<>(this.value);
            }

            @Override
            public IElse<T> thenValue(final T value) {
                return new TrueExpression<>(this.value);
            }

            @Override
            public <X extends Throwable> IElse<T> thenThrow(final Supplier<? extends X> exceptionSupplier) throws X {
                throw exceptionSupplier.get();
            }
        }
    }

    private static class FalseExpression<T> implements IElse<T> {
        @Override
        public T elseGet(final Supplier<T> supplier) {
            return supplier.get();
        }

        @Override
        public T elseValue(final T value) {
            return value;
        }

        @Override
        public IElseIf<T> elseIf(final boolean expression) {
            if (expression) {
                return new TrueElseIf<>();
            }

            return new FalseElseIf<>();
        }

        @Override
        public <X extends Throwable> T elseThrow(final Supplier<? extends X> exceptionSupplier) throws X {
            throw exceptionSupplier.get();
        }

        private static class TrueElseIf<T> implements IElseIf<T> {
            @Override
            public IElse<T> thenGet(final Supplier<T> supplier) {
                return new TrueExpression<>(supplier.get());
            }

            @Override
            public IElse<T> thenValue(final T value) {
                return new TrueExpression<>(value);
            }

            @Override
            public <X extends Throwable> IElse<T> thenThrow(final Supplier<? extends X> exceptionSupplier) throws X {
                throw exceptionSupplier.get();
            }
        }

        private static class FalseElseIf<T> implements IElseIf<T> {
            @Override
            public IElse<T> thenGet(final Supplier<T> supplier) {
                return new FalseExpression<>();
            }

            @Override
            public IElse<T> thenValue(final T value) {
                return new FalseExpression<>();
            }

            @Override
            public <X extends Throwable> IElse<T> thenThrow(final Supplier<? extends X> exceptionSupplier) throws X {
                return new FalseExpression<>();
            }
        }
    }

    private static class TrueExpressionCall implements IElseCall {
        @Override
        public void elseCall(final IProcedure procedure) {
            // does not anything
        }

        @Override
        public <X extends Throwable> void elseThrow(final Supplier<? extends X> exceptionSupplier) throws X {
            // does not anything
        }
    }

    private static class FalseExpressionCall implements IElseCall {
        @Override
        public void elseCall(final IProcedure procedure) {
            procedure.call();
        }

        @Override
        public <X extends Throwable> void elseThrow(final Supplier<? extends X> exceptionSupplier) throws X {
            throw exceptionSupplier.get();
        }
    }
}
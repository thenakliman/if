package com.thenakliman.ifs;

import java.util.function.Supplier;

class IfExpression {
    public interface IProcedure {
        void call();
    }

    interface IValueElseIf<T> {
        IElseIf<T> thenGet(final Supplier<T> supplier);

        IElseIf<T> thenValue(final T value);

        <X extends Throwable> IElseIf<T> thenThrow(final Supplier<? extends X> exceptionSupplier) throws X;
    }

    interface IIExceptionElseIf {
        <T> T elseGet(final Supplier<T> supplier);

        <T> T elseValue(final T value);

        IExceptionElseIf elseIf(final boolean expression);

        <X extends Throwable> void elseThrow(final Supplier<? extends X> exceptionSupplier) throws X;
    }

    interface IExceptionElseIf {
        <T> IElseIf<T> thenGet(final Supplier<T> supplier);

        <T> IElseIf<T> thenValue(final T value);

        <X extends Throwable> IIExceptionElseIf thenThrow(final Supplier<? extends X> exceptionSupplier) throws X;
    }

    interface IElse<T> {
        T elseGet(final Supplier<T> supplier);

        T elseValue(final T value);

        IValueElseIf<T> elseIf(final boolean expression);

        <X extends Throwable> T elseThrow(final Supplier<? extends X> exceptionSupplier) throws X;
    }

    interface IElseIf<T> {
        IValueElseIf<T> elseIf(final boolean expression);

        T elseGet(final Supplier<T> supplier);

        T elseValue(final T value);

        <X extends Throwable> T elseThrow(final Supplier<? extends X> exceptionSupplier) throws X;
    }

    interface IElseCall {
        void elseCall(final IProcedure procedure);

        <X extends Throwable> void elseThrow(final Supplier<? extends X> exceptionSupplier) throws X;
    }

    interface IExpressionThen {
        <T> IElse<T> thenGet(final Supplier<T> supplier);

        <T> IElse<T> thenValue(final T supplier);

        IElseCall thenCall(final IProcedure procedure);

        <X extends Throwable> IExceptionElse thenThrow(final Supplier<? extends X> exceptionSupplier) throws X;
    }

    interface IExceptionElse {
        void elseCall(final IProcedure procedure);

        <T> T elseValue(final T value);

        <X extends Throwable> void elseThrow(final Supplier<? extends X> exceptionSupplier) throws X;

        <T> T elseGet(final Supplier<T> supplier);

        IExceptionElseIf elseIf(final boolean expression);
    }


    static class TrueExpressionThen implements IExpressionThen {
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

    static class FalseExpressionThen implements IExpressionThen {
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
            public <T> IElseIf<T> thenGet(final Supplier<T> supplier) {
                return new FalseElseIf<>();
            }

            @Override
            public <T> IElseIf<T> thenValue(final T value) {
                return new FalseElseIf<>();
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
                    if(expression) {
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
            public <T> IElseIf<T> thenGet(Supplier<T> supplier) {
                return new TrueElseIf<>(supplier.get());
            }

            @Override
            public <T> IElseIf<T> thenValue(T value) {
                return new TrueElseIf<>(value);
            }

            @Override
            public <X extends Throwable> IIExceptionElseIf thenThrow(Supplier<? extends X> exceptionSupplier) throws X {
                throw exceptionSupplier.get();
            }
        }
    }


    static class TrueExpression<T> implements IElse<T> {
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
        public IValueElseIf<T> elseIf(final boolean expression) {
            return new ValueObject<>(this.value);
        }

        @Override
        public <X extends Throwable> T elseThrow(final Supplier<? extends X> exceptionSupplier) throws X {
            return this.value;
        }

        private static class ValueObject<T> implements IValueElseIf<T> {
            final private T value;

            public ValueObject(final T value) {
                this.value = value;
            }

            @Override
            public IElseIf<T> thenGet(final Supplier<T> supplier) {
                return new TrueElseIf<>(this.value);
            }

            @Override
            public IElseIf<T> thenValue(final T value) {
                return new TrueElseIf<>(this.value);
            }

            @Override
            public <X extends Throwable> IElseIf<T> thenThrow(final Supplier<? extends X> exceptionSupplier) throws X {
                throw exceptionSupplier.get();
            }
        }
    }

    public static class TrueElseIf<T> implements IElseIf<T> {
        final private T value;

        public TrueElseIf(final T value) {
            this.value = value;
        }

        @Override
        public IValueElseIf<T> elseIf(final boolean expression) {
            return new TrueExpression.ValueObject<>(this.value);
        }

        @Override
        public T elseGet(final Supplier<T> supplier) {
            return this.value;
        }

        @Override
        public T elseValue(final T value) {
            return this.value;
        }

        @Override
        public <X extends Throwable> T elseThrow(final Supplier<? extends X> exceptionSupplier) throws X {
            return this.value;
        }
    }

    public static class FalseElseIf<T> implements IElseIf<T> {
        @Override
        public IValueElseIf<T> elseIf(final boolean expression) {
            if (expression) {
                return new FalseExpression.NoValueTrueObject<>();
            }

            return new FalseExpression.FalseValueObject<>();
        }

        @Override
        public T elseGet(final Supplier<T> supplier) {
            return supplier.get();
        }

        @Override
        public T elseValue(final T value) {
            return value;
        }

        @Override
        public <X extends Throwable> T elseThrow(final Supplier<? extends X> exceptionSupplier) throws X {
            throw exceptionSupplier.get();
        }
    }

    static class FalseExpression<T> implements IElse<T> {
        @Override
        public T elseGet(final Supplier<T> supplier) {
            return supplier.get();
        }

        @Override
        public T elseValue(final T value) {
            return value;
        }

        @Override
        public IValueElseIf<T> elseIf(final boolean expression) {
            if (expression) {
                return new NoValueTrueObject<>();
            }

            return new FalseValueObject<>();
        }

        @Override
        public <X extends Throwable> T elseThrow(final Supplier<? extends X> exceptionSupplier) throws X {
            throw exceptionSupplier.get();
        }

        private static class NoValueTrueObject<T> implements IValueElseIf<T> {
            @Override
            public IElseIf<T> thenGet(final Supplier<T> supplier) {
                return new TrueElseIf<>(supplier.get());
            }

            @Override
            public IElseIf<T> thenValue(final T value) {
                return new TrueElseIf<>(value);
            }

            @Override
            public <X extends Throwable> IElseIf<T> thenThrow(final Supplier<? extends X> exceptionSupplier) throws X {
                throw exceptionSupplier.get();
            }
        }

        private static class FalseValueObject<T> implements IValueElseIf<T> {
            @Override
            public IElseIf<T> thenGet(final Supplier<T> supplier) {
                return new FalseElseIf<>();
            }

            @Override
            public IElseIf<T> thenValue(final T value) {
                return new FalseElseIf<>();
            }

            @Override
            public <X extends Throwable> IElseIf<T> thenThrow(final Supplier<? extends X> exceptionSupplier) throws X {
                return new FalseElseIf<>();
            }
        }
    }

    static class TrueExpressionCall implements IElseCall {
        @Override
        public void elseCall(final IProcedure procedure) {
            // does not anything
        }

        @Override
        public <X extends Throwable> void elseThrow(final Supplier<? extends X> exceptionSupplier) throws X {
            // does not anything
        }
    }

    static class FalseExpressionCall implements IElseCall {
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

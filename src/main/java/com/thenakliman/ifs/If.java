package com.thenakliman.ifs;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

final class If {
    private If() {
        // Not allowed to create instance
    }

    public static <T> NullTernary.IThen<T> isNull(T value) {
        if (Objects.isNull(value)) {
            return new NullTernary.NullThen<>();
        }

        return new NullTernary.NonNullThen<>(value);
    }

    public static IfExpression.IExpressionThen isTrue(Boolean expression) {
        if (expression) {
            return new IfExpression.TrueExpressionThen();
        }

        return new IfExpression.FalseExpressionThen();
    }

    public static IfExpression.IExpressionThen isFalse(Boolean expression) {
        if (!expression) {
            return new IfExpression.TrueExpressionThen();
        }

        return new IfExpression.FalseExpressionThen();
    }

    public static IfOnly.IExecute isTrueThen(Boolean expression) {
        if (expression) {
            return new IfOnly.Execution();
        }

        return new IfOnly.SkipExecution();
    }

    public static IfOnly.IExecute isFalseThen(Boolean expression) {
        if (expression) {
            return new IfOnly.SkipExecution();
        }

        return new IfOnly.Execution();
    }

    public static <T> T orElse(Supplier<Boolean> supplier, Supplier<T> ifSupplier, Supplier<T> elseSupplier) {
        if (supplier.get()) {
            return ifSupplier.get();
        }

        return elseSupplier.get();
    }

    public static <T, R> R nullOrElse(T value, Supplier<R> ifSupplier, Supplier<R> elseSupplier) {
        if (Objects.isNull(value)) {
            return ifSupplier.get();
        }

        return elseSupplier.get();
    }

    public static <T, R> R nullOrElse(T value, Supplier<R> ifSupplier, Function<T, R> elseFunction) {
        if (Objects.isNull(value)) {
            return ifSupplier.get();
        }

        return elseFunction.apply(value);
    }

    public static void orElse(Supplier<Boolean> supplier, IfExpression.IProcedure ifProcessor, IfExpression.IProcedure elseProcessor) {
        if (supplier.get()) {
            ifProcessor.call();
        }

        elseProcessor.call();
    }

    public static <T> T orElse(boolean expression, Supplier<T> ifSupplier, Supplier<T> elseSupplier) {
        if (expression) {
            return ifSupplier.get();
        }

        return elseSupplier.get();
    }

    public static void orElse(boolean expression, IfExpression.IProcedure ifProcessor, IfExpression.IProcedure elseProcessor) {
        if (expression) {
            ifProcessor.call();
        }

        elseProcessor.call();
    }

    public static <T> T orElse(boolean expression, T thenValue, T elseValue) {
        if (expression) {
            return thenValue;
        }

        return elseValue;
    }

    public static <T> T orElse(Supplier<Boolean> supplier, T thenValue, T elseValue) {
        if (supplier.get()) {
            return thenValue;
        }

        return elseValue;
    }

    public static void isTrueThen(boolean expression, IfExpression.IProcedure ifProcessor) {
        if (expression) {
            ifProcessor.call();
        }
    }

    public static void isTrueThen(Supplier<Boolean> supplier, IfExpression.IProcedure ifProcessor) {
        if (supplier.get()) {
            ifProcessor.call();
        }
    }

    public static <T> void isNullThen(T value, IfExpression.IProcedure ifProcessor) {
        if (Objects.isNull(value)) {
            ifProcessor.call();
        }
    }
}

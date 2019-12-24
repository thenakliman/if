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

    public static IfExpression.IExpressionThen expressionIsTrue(Boolean expressionResult) {
        if (expressionResult) {
            return new IfExpression.TrueExpressionThen();
        }

        return new IfExpression.FalseExpressionThen();
    }

    public static IfExpression.IExpressionThen expressionIsFalse(Boolean expressionResult) {
        if (!expressionResult) {
            return new IfExpression.TrueExpressionThen();
        }

        return new IfExpression.FalseExpressionThen();
    }

    public static IfOnly.IExecute isTrue(Boolean expressionResult) {
        if (expressionResult) {
            return new IfOnly.Execution();
        }

        return new IfOnly.SkipExecution();
    }

    public static IfOnly.IExecute isFalse(Boolean expressionResult) {
        if (expressionResult) {
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

    public static <T> T orElse(boolean expressionResult, Supplier<T> ifSupplier, Supplier<T> elseSupplier) {
        if (expressionResult) {
            return ifSupplier.get();
        }

        return elseSupplier.get();
    }

    public static void orElse(boolean expressionResult, IfExpression.IProcedure ifProcessor, IfExpression.IProcedure elseProcessor) {
        if (expressionResult) {
            ifProcessor.call();
        }

        elseProcessor.call();
    }

    public static <T> T orElse(boolean expressionResult, T thenValue, T elseValue) {
        if (expressionResult) {
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

    public static void ifTrue(boolean expressionResult, IfExpression.IProcedure ifProcessor) {
        if (expressionResult) {
            ifProcessor.call();
        }
    }

    public static <T> void isNull(T value, IfExpression.IProcedure ifProcessor) {
        if (Objects.isNull(value)) {
            ifProcessor.call();
        }
    }

    public static void ifTrue(Supplier<Boolean> supplier, IfExpression.IProcedure ifProcessor) {
        if (supplier.get()) {
            ifProcessor.call();
        }
    }
}

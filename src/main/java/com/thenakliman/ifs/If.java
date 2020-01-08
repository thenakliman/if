package com.thenakliman.ifs;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

final class If {
    private If() {
        // Not allowed to create instance
    }

    public static <T> NullTernary.IThen<T> isNull(final T value) {
        if (Objects.isNull(value)) {
            return new NullTernary.NullThen<>();
        }

        return new NullTernary.NonNullThen<>(value);
    }

    public static IfExpression.IExpressionThen isTrue(final Boolean expression) {
        if (expression) {
            return new IfExpression.TrueExpressionThen();
        }

        return new IfExpression.FalseExpressionThen();
    }

    public static IfExpression.IExpressionThen isFalse(final Boolean expression) {
        if (!expression) {
            return new IfExpression.TrueExpressionThen();
        }

        return new IfExpression.FalseExpressionThen();
    }

    public static IfOnly.IExecute isTrueThen(final Boolean expression) {
        if (expression) {
            return new IfOnly.Execution();
        }

        return new IfOnly.SkipExecution();
    }

    public static IfOnly.IExecute isFalseThen(final Boolean expression) {
        if (expression) {
            return new IfOnly.SkipExecution();
        }

        return new IfOnly.Execution();
    }


    public static <T> T orElse(final boolean expression,
                               final Supplier<T> ifSupplier,
                               final Supplier<T> elseSupplier) {
        if (expression) {
            return ifSupplier.get();
        }

        return elseSupplier.get();
    }

    public static <T> T orElse(final Supplier<Boolean> supplier,
                               final Supplier<T> ifSupplier,
                               final Supplier<T> elseSupplier) {

        return orElse(supplier.get(), ifSupplier, elseSupplier);
    }

    public static <T, R> R nullOrElse(final T value,
                                      final Supplier<R> ifSupplier,
                                      final Supplier<R> elseSupplier) {

        return orElse(Objects.isNull(value), ifSupplier, elseSupplier);
    }

    public static <T, R> R nullOrElse(final T value,
                                      final Supplier<R> ifSupplier,
                                      final Function<T, R> elseFunction) {

        return orElse(Objects.isNull(value), ifSupplier, () -> elseFunction.apply(value));
    }

    public static void orElse(final Supplier<Boolean> supplier,
                              final IfExpression.IProcedure ifProcessor,
                              final IfExpression.IProcedure elseProcessor) {

        orElse(supplier.get(), ifProcessor, elseProcessor);
    }

    public static void orElse(final boolean expression,
                              final IfExpression.IProcedure ifProcessor,
                              final IfExpression.IProcedure elseProcessor) {
        if (expression) {
            ifProcessor.call();
        }

        elseProcessor.call();
    }

    public static <T> T orElse(final boolean expression, final T thenValue, final T elseValue) {
        return orElse(expression, () -> thenValue, () -> elseValue);
    }

    public static <T> T orElse(final Supplier<Boolean> supplier, final T thenValue, final T elseValue) {
        return orElse(supplier.get(), () -> thenValue, () -> elseValue);
    }

    public static void isTrueThen(final boolean expression, final IfExpression.IProcedure ifProcessor) {
        if (expression) {
            ifProcessor.call();
        }
    }

    public static void isTrueThen(final Supplier<Boolean> supplier, final IfExpression.IProcedure ifProcessor) {
        isTrueThen(supplier.get(), ifProcessor);
    }

    public static <T> void isNullThen(final T value, final IfExpression.IProcedure ifProcessor) {
        isTrueThen(Objects.isNull(value), ifProcessor);
    }
}

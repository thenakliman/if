package com.thenakliman.ifs;

import java.util.Objects;

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
}

package com.thenakliman.If;

import java.util.Objects;

final class If {
    private If() {
        // Not allowed to create instance
    }

    public static <T> Ternary.IThen<T> isNull(T value) {
        if (Objects.isNull(value)) {
            return new Ternary.NullThen<>();
        }

        return new Ternary.NonNullThen<>(value);
    }

    public static TernaryExpressionEvaluator.IThenGet expressionIsTrue(Boolean expressionResult) {
        if (expressionResult) {
            return new TernaryExpressionEvaluator.TrueThen();
        }

        return new TernaryExpressionEvaluator.FalseThen();
    }

    public static Then.SingularCall isTrue(Boolean expressionResult) {
        if (expressionResult) {
            return new Then.IThen();
        }

        return new Then.DontCall();
    }
}

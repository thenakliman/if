package com.thenakliman.If;


import java.util.function.Supplier;

public class Then {

    public interface SingularCall {
        void thenCall(TernaryExpressionEvaluator.IProcedure procedure);

        <X extends Throwable> void thenThrow(Supplier<? extends X> exceptionSupplier) throws X;
    }

    public static class IThen implements SingularCall {
        public void thenCall(TernaryExpressionEvaluator.IProcedure procedure) {
            procedure.call();
        }

        public <X extends Throwable> void thenThrow(Supplier<? extends X> exceptionSupplier) throws X {
            throw exceptionSupplier.get();
        }
    }

    public static class DontCall implements SingularCall {
        public void thenCall(TernaryExpressionEvaluator.IProcedure procedure) {
            // do not call
        }

        public <X extends Throwable> void thenThrow(Supplier<? extends X> exceptionSupplier) throws X {
            // do not throw exception
        }
    }
}

package com.thenakliman.ifs;


import java.util.function.Supplier;

final class IfOnly {

    public interface IExecute {
        void thenCall(IfExpression.IProcedure procedure);

        <X extends Throwable> void thenThrow(Supplier<? extends X> exceptionSupplier) throws X;
    }

    public static class Execution implements IExecute {
        public void thenCall(IfExpression.IProcedure procedure) {
            procedure.call();
        }

        public <X extends Throwable> void thenThrow(Supplier<? extends X> exceptionSupplier) throws X {
            throw exceptionSupplier.get();
        }
    }

    public static class SkipExecution implements IExecute {
        public void thenCall(IfExpression.IProcedure procedure) {
            // do not call
        }

        public <X extends Throwable> void thenThrow(Supplier<? extends X> exceptionSupplier) throws X {
            // do not throw exception
        }
    }
}

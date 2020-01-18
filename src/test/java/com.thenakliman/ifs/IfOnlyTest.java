package com.thenakliman.ifs;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.mockito.Mockito.*;

public class IfOnlyTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void isTrueThen_callThenCall_whenExpressionEvaluatesToTrue() {
        CallTestHelper callTestHelper = mock(CallTestHelper.class);

        If.isTrueThen(true, callTestHelper::thenCallMe1);

        verify(callTestHelper).thenCallMe1();
    }

    @Test
    public void isTrueThen_thenCall_doNotCallSupplier_whenExpressionEvaluatesToFalse() {
        CallTestHelper callTestHelper = mock(CallTestHelper.class);

        If.isTrueThen(false, callTestHelper::thenCallMe1);

        verify(callTestHelper, times(0)).thenCallMe1();
    }

    @Test
    public void isTrueThen_thenCall_callThenCall_whenSupplierEvaluatesToFalse() {
        CallTestHelper callTestHelper = mock(CallTestHelper.class);

        If.isTrueThen(() -> false, callTestHelper::thenCallMe1);

        verify(callTestHelper, times(0)).thenCallMe1();
    }

    @Test
    public void isTrueThen_thenCall_callThenCall_whenSupplierEvaluatesToTrue() {
        CallTestHelper callTestHelper = mock(CallTestHelper.class);

        If.isTrueThen(() -> true, callTestHelper::thenCallMe1);

        verify(callTestHelper).thenCallMe1();
    }

    @Test
    public void isTrueThen_thenThrow_whenExpressionIsTrue() {
        expectedException.expect(IllegalArgumentException.class);
        If.isTrueThen(true).thenThrow(IllegalArgumentException::new);
    }

    @Test
    public void isTrueThen_thenThrow_doNotThrowException_whenExpressionIsFalse() {
        If.isTrueThen(false).thenThrow(IllegalArgumentException::new);
    }

    @Test
    public void isTrueThen_thenCall_callThenCall_whenExpressionIsTrue() {
        CallTestHelper callTestHelper = mock(CallTestHelper.class);

        If.isTrueThen(true).thenCall(callTestHelper::thenCallMe1);

        verify(callTestHelper).thenCallMe1();
    }

    @Test
    public void isTrueThen_thenCall_doNotCall_whenExpressionIsFalse() {
        CallTestHelper callTestHelper = mock(CallTestHelper.class);

        If.isTrueThen(false).thenCall(callTestHelper::thenCallMe1);

        verify(callTestHelper, times(0)).thenCallMe1();
    }
}

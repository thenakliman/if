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
        IfTest.TestHelper testHelper = mock(IfTest.TestHelper.class);

        If.isTrueThen(true, testHelper::thenCallMe);

        verify(testHelper).thenCallMe();
    }

    @Test
    public void isTrueThen_thenCall_doNotCallSupplier_whenExpressionEvaluatesToFalse() {
        IfTest.TestHelper testHelper = mock(IfTest.TestHelper.class);

        If.isTrueThen(false, testHelper::thenCallMe);

        verify(testHelper, times(0)).thenCallMe();
    }

    @Test
    public void isTrueThen_thenCall_callThenCall_whenSupplierEvaluatesToFalse() {
        IfTest.TestHelper testHelper = mock(IfTest.TestHelper.class);

        If.isTrueThen(() -> false, testHelper::thenCallMe);

        verify(testHelper, times(0)).thenCallMe();
    }

    @Test
    public void isTrueThen_thenCall_callThenCall_whenSupplierEvaluatesToTrue() {
        IfTest.TestHelper testHelper = mock(IfTest.TestHelper.class);

        If.isTrueThen(() -> true, testHelper::thenCallMe);

        verify(testHelper).thenCallMe();
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
        IfTest.TestHelper testHelper = mock(IfTest.TestHelper.class);

        If.isTrueThen(true).thenCall(testHelper::thenCallMe);

        verify(testHelper).thenCallMe();
    }

    @Test
    public void isTrueThen_thenCall_doNotCall_whenExpressionIsFalse() {
        IfTest.TestHelper testHelper = mock(IfTest.TestHelper.class);

        If.isTrueThen(false).thenCall(testHelper::thenCallMe);

        verify(testHelper, times(0)).thenCallMe();
    }
}

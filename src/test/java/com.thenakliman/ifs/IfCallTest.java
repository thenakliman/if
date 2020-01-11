package com.thenakliman.ifs;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.mockito.Mockito.*;

public class IfCallTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void isTrue_thenCallElseCall_callThenCall_whenExpressionIsTrue() {
        IfTest.TestHelper testHelper = mock(IfTest.TestHelper.class);
        If.isTrue(true)
                .thenCall(testHelper::thenCallMe)
                .elseCall(testHelper::elseCallMe);
        verify(testHelper).thenCallMe();
    }

    @Test
    public void isTrue_thenCallElseCall_callElseCall_whenExpressionIsFalse() {
        IfTest.TestHelper testHelper = mock(IfTest.TestHelper.class);
        If.isTrue(false)
                .thenCall(testHelper::thenCallMe)
                .elseCall(testHelper::elseCallMe);
        verify(testHelper).elseCallMe();
    }

    @Test
    public void isTrue_thenCallElseThrow_throwElseThrowException_whenExpressionIsFalse() {
        IfTest.TestHelper testHelper = mock(IfTest.TestHelper.class);

        expectedException.expect(IllegalArgumentException.class);
        If.isTrue(false)
                .thenCall(testHelper::thenCallMe)
                .elseThrow(IllegalArgumentException::new);
    }

    @Test
    public void isTrue_thenCallElseThrow_callThenCall_whenExpressionIsTrue() {
        IfTest.TestHelper testHelper = mock(IfTest.TestHelper.class);

        If.isTrue(true)
                .thenCall(testHelper::thenCallMe)
                .elseThrow(IllegalArgumentException::new);

        verify(testHelper).thenCallMe();
    }

    @Test
    public void isFalse_thenCallElseCall_callThenCall_whenExpressionIsFalse() {
        IfTest.TestHelper testHelper = mock(IfTest.TestHelper.class);
        If.isFalse(false)
                .thenCall(testHelper::thenCallMe)
                .elseCall(testHelper::elseCallMe);
        verify(testHelper).thenCallMe();
    }

    @Test
    public void isFalse_thenCallElseCall_callElseCall_whenExpressionIsTrue() {
        IfTest.TestHelper testHelper = mock(IfTest.TestHelper.class);
        If.isFalse(true)
                .thenCall(testHelper::thenCallMe)
                .elseCall(testHelper::elseCallMe);
        verify(testHelper).elseCallMe();
    }


    @Test
    public void isFalse_thenCallElseThrow_throwElseException_whenExpressionIsTrue() {
        IfTest.TestHelper testHelper = mock(IfTest.TestHelper.class);

        expectedException.expect(IllegalArgumentException.class);
        If.isFalse(true)
                .thenCall(testHelper::thenCallMe)
                .elseThrow(IllegalArgumentException::new);
    }

    @Test
    public void isFalse_thenCallElseThrow_callThenCall_whenExpressionIsFalse() {
        IfTest.TestHelper testHelper = mock(IfTest.TestHelper.class);

        If.isFalse(false)
                .thenCall(testHelper::thenCallMe)
                .elseThrow(IllegalArgumentException::new);

        verify(testHelper).thenCallMe();
    }

    @Test
    public void isFalseThen_thenCall_callThenCall_whenExpressionIsTrue() {
        IfTest.TestHelper testHelper = mock(IfTest.TestHelper.class);

        If.isFalseThen(false).thenCall(testHelper::thenCallMe);

        verify(testHelper).thenCallMe();
    }

    @Test
    public void isFalseThen_thenCall_doNotCall_whenExpressionIsFalse() {
        IfTest.TestHelper testHelper = mock(IfTest.TestHelper.class);

        If.isFalseThen(true).thenCall(testHelper::thenCallMe);

        verify(testHelper, times(0)).thenCallMe();
    }
}

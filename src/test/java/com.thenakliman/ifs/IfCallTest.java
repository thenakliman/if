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
        TestHelper testHelper = mock(TestHelper.class);
        If.isTrue(true)
                .thenCall(testHelper::thenCallMe1)
                .elseCall(testHelper::elseCallMe);
        verify(testHelper).thenCallMe1();
    }

    @Test
    public void isTrue_thenCallElseCall_callElseCall_whenExpressionIsFalse() {
        TestHelper testHelper = mock(TestHelper.class);
        If.isTrue(false)
                .thenCall(testHelper::thenCallMe1)
                .elseCall(testHelper::elseCallMe);
        verify(testHelper).elseCallMe();
    }

    @Test
    public void isTrue_thenCallElseThrow_throwElseThrowException_whenExpressionIsFalse() {
        TestHelper testHelper = mock(TestHelper.class);

        expectedException.expect(IllegalArgumentException.class);
        If.isTrue(false)
                .thenCall(testHelper::thenCallMe1)
                .elseThrow(IllegalArgumentException::new);
    }

    @Test
    public void isTrue_thenCallElseThrow_callThenCall_whenExpressionIsTrue() {
        TestHelper testHelper = mock(TestHelper.class);

        If.isTrue(true)
                .thenCall(testHelper::thenCallMe1)
                .elseThrow(IllegalArgumentException::new);

        verify(testHelper).thenCallMe1();
    }

    @Test
    public void isFalse_thenCallElseCall_callThenCall_whenExpressionIsFalse() {
        TestHelper testHelper = mock(TestHelper.class);
        If.isFalse(false)
                .thenCall(testHelper::thenCallMe1)
                .elseCall(testHelper::elseCallMe);
        verify(testHelper).thenCallMe1();
    }

    @Test
    public void isFalse_thenCallElseCall_callElseCall_whenExpressionIsTrue() {
        TestHelper testHelper = mock(TestHelper.class);
        If.isFalse(true)
                .thenCall(testHelper::thenCallMe1)
                .elseCall(testHelper::elseCallMe);
        verify(testHelper).elseCallMe();
    }


    @Test
    public void isFalse_thenCallElseThrow_throwElseException_whenExpressionIsTrue() {
        TestHelper testHelper = mock(TestHelper.class);

        expectedException.expect(IllegalArgumentException.class);
        If.isFalse(true)
                .thenCall(testHelper::thenCallMe1)
                .elseThrow(IllegalArgumentException::new);
    }

    @Test
    public void isFalse_thenCallElseThrow_callThenCall_whenExpressionIsFalse() {
        TestHelper testHelper = mock(TestHelper.class);

        If.isFalse(false)
                .thenCall(testHelper::thenCallMe1)
                .elseThrow(IllegalArgumentException::new);

        verify(testHelper).thenCallMe1();
    }

    @Test
    public void isFalseThen_thenCall_callThenCall_whenExpressionIsTrue() {
        TestHelper testHelper = mock(TestHelper.class);

        If.isFalseThen(false).thenCall(testHelper::thenCallMe1);

        verify(testHelper).thenCallMe1();
    }

    @Test
    public void isFalseThen_thenCall_doNotCall_whenExpressionIsFalse() {
        TestHelper testHelper = mock(TestHelper.class);

        If.isFalseThen(true).thenCall(testHelper::thenCallMe1);

        verify(testHelper, times(0)).thenCallMe1();
    }

    @Test
    public void isTrue_thenCallElseIfThenCallElseCall_callFirstThenCall() {
        TestHelper testHelper = mock(TestHelper.class);
        If.isTrue(true)
                .thenCall(testHelper::thenCallMe1)
                .elseIf(true)
                .thenCall(testHelper::thenCallMe2)
                .elseCall(testHelper::elseCallMe);
        verify(testHelper).thenCallMe1();
    }

    @Test
    public void isTrue_thenCallElseIfThenCallElseCall_whenIsTrue_dontCallSecondThenCall() {
        TestHelper testHelper = mock(TestHelper.class);
        If.isTrue(true)
                .thenCall(testHelper::thenCallMe1)
                .elseIf(true)
                .thenCall(testHelper::thenCallMe2)
                .elseCall(testHelper::elseCallMe);

        verify(testHelper, times(0)).thenCallMe2();
    }

    @Test
    public void isTrue_thenCallElseIfThenCallElseCall_whenIsTrue_dontCallElseCall() {
        TestHelper testHelper = mock(TestHelper.class);
        If.isTrue(true)
                .thenCall(testHelper::thenCallMe1)
                .elseIf(true)
                .thenCall(testHelper::thenCallMe2)
                .elseCall(testHelper::elseCallMe);
        verify(testHelper, times(0)).elseCallMe();
    }

    @Test
    public void isTrue_thenCallElseIfThenCallElseCall_callSecondThenCall() {
        TestHelper testHelper = mock(TestHelper.class);
        If.isTrue(false)
                .thenCall(testHelper::thenCallMe1)
                .elseIf(true)
                .thenCall(testHelper::thenCallMe2)
                .elseCall(testHelper::elseCallMe);
        verify(testHelper).thenCallMe2();
    }

    @Test
    public void isTrue_thenCallElseIfThenCallElseCall_whenFirstElseIfIsTrue_dontCallFirstThenCall() {
        TestHelper testHelper = mock(TestHelper.class);
        If.isTrue(false)
                .thenCall(testHelper::thenCallMe1)
                .elseIf(true)
                .thenCall(testHelper::thenCallMe2)
                .elseCall(testHelper::elseCallMe);
        verify(testHelper, times(0)).thenCallMe1();
    }

    @Test
    public void isTrue_thenCallElseIfThenCallElseCall_whenFirstElseIfIsTrue_dontCallElseCall() {
        TestHelper testHelper = mock(TestHelper.class);
        If.isTrue(false)
                .thenCall(testHelper::thenCallMe1)
                .elseIf(true)
                .thenCall(testHelper::thenCallMe2)
                .elseCall(testHelper::elseCallMe);
        verify(testHelper, times(0)).thenCallMe1();
    }

    @Test
    public void isTrue_thenCallElseIfThenCallElseCall_callElseCall() {
        TestHelper testHelper = mock(TestHelper.class);
        If.isTrue(false)
                .thenCall(testHelper::thenCallMe1)
                .elseIf(false)
                .thenCall(testHelper::thenCallMe2)
                .elseCall(testHelper::elseCallMe);
        verify(testHelper).elseCallMe();
    }

    @Test
    public void isTrue_thenCallElseIfThenCallElseCall_callElseCall_dontCallFirstThenCall() {
        TestHelper testHelper = mock(TestHelper.class);
        If.isTrue(false)
                .thenCall(testHelper::thenCallMe1)
                .elseIf(false)
                .thenCall(testHelper::thenCallMe2)
                .elseCall(testHelper::elseCallMe);

        verify(testHelper, times(0)).thenCallMe1();
    }

    @Test
    public void isTrue_thenCallElseIfThenCallElseCall_callElseCall_dontCallSecondThenCall() {
        TestHelper testHelper = mock(TestHelper.class);
        If.isTrue(false)
                .thenCall(testHelper::thenCallMe1)
                .elseIf(false)
                .thenCall(testHelper::thenCallMe2)
                .elseCall(testHelper::elseCallMe);

        verify(testHelper, times(0)).thenCallMe2();
    }

    @Test
    public void isTrue_thenCallElseIfThenCallElseThrow_throwElseThrowException() {
        TestHelper testHelper = mock(TestHelper.class);
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("illegal");
        If.isTrue(false)
                .thenCall(testHelper::thenCallMe1)
                .elseIf(false)
                .thenCall(testHelper::thenCallMe2)
                .elseThrow(() -> new IllegalArgumentException("illegal"));
    }

    @Test
    public void isTrue_thenCallElseIfThenCallElseThrow_throwElseThrowException_dontCallFirstThenCall() {
        TestHelper testHelper = mock(TestHelper.class);
        try {
            If.isTrue(false)
                    .thenCall(testHelper::thenCallMe1)
                    .elseIf(false)
                    .thenCall(testHelper::thenCallMe2)
                    .elseThrow(() -> new IllegalArgumentException("illegal"));
        } catch (IllegalArgumentException exception) {
            // do nothing
        }

        verify(testHelper, times(0)).thenCallMe1();
    }

    @Test
    public void isTrue_thenCallElseIfThenCallElseThrow_throwElseThrowException_dontCallSecondThenCall() {
        TestHelper testHelper = mock(TestHelper.class);
        try {
            If.isTrue(false)
                    .thenCall(testHelper::thenCallMe1)
                    .elseIf(false)
                    .thenCall(testHelper::thenCallMe2)
                    .elseThrow(() -> new IllegalArgumentException("illegal"));
        } catch (IllegalArgumentException exception) {
            // do nothing
        }

        verify(testHelper, times(0)).thenCallMe2();
    }

    @Test
    public void isTrue_thenCallElseIfThenThrowElseThrow_throwThenThrowException() {
        TestHelper testHelper = mock(TestHelper.class);
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("illegal");

        If.isTrue(false)
                .thenCall(testHelper::thenCallMe1)
                .elseIf(false)
                .thenThrow(() -> new RuntimeException("runtime"))
                .elseThrow(() -> new IllegalArgumentException("illegal"));
    }

    @Test
    public void isTrue_thenCallElseIfThenThrowElseThrow_throwThenThrowException_dontCallThenCall() {
        TestHelper testHelper = mock(TestHelper.class);
        try {
            If.isTrue(false)
                    .thenCall(testHelper::thenCallMe1)
                    .elseIf(false)
                    .thenThrow(() -> new RuntimeException("runtime"))
                    .elseThrow(() -> new IllegalArgumentException("illegal"));
        } catch (RuntimeException exception) {
            // do nothing
        }

        verify(testHelper, times(0)).thenCallMe1();
    }

    @Test
    public void isTrue_thenCallElseIfThenThrowElseCall_callElseCall() {
        TestHelper testHelper = mock(TestHelper.class);
        If.isTrue(false)
                .thenCall(testHelper::thenCallMe1)
                .elseIf(false)
                .thenThrow(() -> new RuntimeException("runtime"))
                .elseCall(testHelper::elseCallMe);

        verify(testHelper).elseCallMe();
    }

    @Test
    public void isTrue_thenCallElseIfThenThrowElseThrow_callElseCall_dontCallThenCall() {
        TestHelper testHelper = mock(TestHelper.class);
        If.isTrue(false)
                .thenCall(testHelper::thenCallMe1)
                .elseIf(false)
                .thenThrow(() -> new RuntimeException("runtime"))
                .elseCall(testHelper::elseCallMe);

        verify(testHelper, times(0)).thenCallMe1();
    }

    @Test
    public void isTrue_thenThrowElseIfThenCallElseCall_callElseCall() {
        TestHelper testHelper = mock(TestHelper.class);
        If.isTrue(false)
                .thenThrow(() -> new RuntimeException("runtime"))
                .elseIf(false)
                .thenCall(testHelper::thenCallMe2)
                .elseCall(testHelper::elseCallMe);

        verify(testHelper).elseCallMe();
    }

    @Test
    public void isTrue_thenThrowElseIfThenCallElseCall_callElseCall_dontCallThenCall() {
        TestHelper testHelper = mock(TestHelper.class);
        If.isTrue(false)
                .thenThrow(() -> new RuntimeException("runtime"))
                .elseIf(false)
                .thenCall(testHelper::thenCallMe2)
                .elseCall(testHelper::elseCallMe);

        verify(testHelper, times(0)).thenCallMe2();
    }

    @Test
    public void isTrue_thenThrowElseIfThenCallElseCall_throwException() {
        TestHelper testHelper = mock(TestHelper.class);
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("runtime");
        If.isTrue(true)
                .thenThrow(() -> new RuntimeException("runtime"))
                .elseIf(false)
                .thenCall(testHelper::thenCallMe2)
                .elseCall(testHelper::elseCallMe);
    }

    @Test
    public void isTrue_thenThrowElseIfThenCallElseCall_throwException_dontCallThenCall() {
        TestHelper testHelper = mock(TestHelper.class);
        try {
            If.isTrue(true)
                    .thenThrow(() -> new RuntimeException("runtime"))
                    .elseIf(false)
                    .thenCall(testHelper::thenCallMe2)
                    .elseCall(testHelper::elseCallMe);
        } catch (RuntimeException exception) {
            // do nothing
        }

        verify(testHelper, times(0)).thenCallMe2();
    }

    @Test
    public void isTrue_thenThrowElseIfThenCallElseCall_throwException_dontCallElseCall() {
        TestHelper testHelper = mock(TestHelper.class);
        try {
            If.isTrue(true)
                    .thenThrow(() -> new RuntimeException("runtime"))
                    .elseIf(false)
                    .thenCall(testHelper::thenCallMe2)
                    .elseCall(testHelper::elseCallMe);
        } catch (RuntimeException exception) {
            // do nothing
        }

        verify(testHelper, times(0)).elseCallMe();
    }

    @Test
    public void isTrue_thenThrowElseIfThenThrowElseThrow_throwFirstException() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("runtime");
        If.isTrue(true)
                .thenThrow(() -> new RuntimeException("runtime"))
                .elseIf(false)
                .thenThrow(() -> new IllegalArgumentException("illegal"))
                .elseThrow(() -> new IllegalMonitorStateException("monitoring"));
    }

    @Test
    public void isTrue_thenThrowElseIfThenThrowElseThrow_throwSecondException() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("illegal");
        If.isTrue(false)
                .thenThrow(() -> new RuntimeException("runtime"))
                .elseIf(true)
                .thenThrow(() -> new IllegalArgumentException("illegal"))
                .elseThrow(() -> new IllegalMonitorStateException("monitoring"));
    }

    @Test
    public void isTrue_thenThrowElseIfThenThrowElseThrow_throwElseException() {
        expectedException.expect(IllegalMonitorStateException.class);
        expectedException.expectMessage("monitoring");
        If.isTrue(false)
                .thenThrow(() -> new RuntimeException("runtime"))
                .elseIf(false)
                .thenThrow(() -> new IllegalArgumentException("illegal"))
                .elseThrow(() -> new IllegalMonitorStateException("monitoring"));
    }

    @Test
    public void isTrue_thenCallElseDoNothing_callThenCall_whenExpressionIsTrue() {
        IfTest.TestHelper testHelper = mock(IfTest.TestHelper.class);
        If.isTrue(true)
                .thenCall(testHelper::thenCallMe1)
                .elseDoNothing();
        verify(testHelper).thenCallMe1();
    }

    @Test
    public void isTrue_thenCallElseDoNothing_dontCallThenCall_whenExpressionIsFalse() {
        IfTest.TestHelper testHelper = mock(IfTest.TestHelper.class);
        If.isTrue(false)
                .thenCall(testHelper::thenCallMe1)
                .elseDoNothing();
        verify(testHelper, times(0)).thenCallMe1();
    }

    @Test
    public void isTrue_thenCallElseIfThenCallElseDoNothing_dontCallSecondThenCall() {
        IfTest.TestHelper testHelper = mock(IfTest.TestHelper.class);
        If.isTrue(true)
                .thenCall(testHelper::thenCallMe1)
                .elseIf(false)
                .thenCall(testHelper::thenCallMe2)
                .elseDoNothing();
        verify(testHelper, times(0)).thenCallMe2();
    }

    @Test
    public void isTrue_thenCallElseIfThenCallElseDoNothing_callFirstThenCall() {
        IfTest.TestHelper testHelper = mock(IfTest.TestHelper.class);
        If.isTrue(true)
                .thenCall(testHelper::thenCallMe1)
                .elseIf(false)
                .thenCall(testHelper::thenCallMe2)
                .elseDoNothing();
        verify(testHelper).thenCallMe1();
    }

    @Test
    public void isTrue_thenCallElseIfThenCallElseDoNothing_dontCallFirstThenCall_whenElseIfIsTrue() {
        IfTest.TestHelper testHelper = mock(IfTest.TestHelper.class);
        If.isTrue(false)
                .thenCall(testHelper::thenCallMe1)
                .elseIf(true)
                .thenCall(testHelper::thenCallMe2)
                .elseDoNothing();
        verify(testHelper, times(0)).thenCallMe1();
    }

    @Test
    public void isTrue_thenCallElseIfThenCallElseDoNothing_callSecondThenCall_whenElseIfIsTrue() {
        IfTest.TestHelper testHelper = mock(IfTest.TestHelper.class);
        If.isTrue(false)
                .thenCall(testHelper::thenCallMe1)
                .elseIf(true)
                .thenCall(testHelper::thenCallMe2)
                .elseDoNothing();
        verify(testHelper).thenCallMe2();
    }

    @Test
    public void isTrue_thenCallElseIfThenCallElseDoNothing_dontCallFirstThenCall_whenAllExpressionAreFalse() {
        IfTest.TestHelper testHelper = mock(IfTest.TestHelper.class);
        If.isTrue(false)
                .thenCall(testHelper::thenCallMe1)
                .elseIf(false)
                .thenCall(testHelper::thenCallMe2)
                .elseDoNothing();
        verify(testHelper, times(0)).thenCallMe1();
    }

    @Test
    public void isTrue_thenCallElseIfThenCallElseDoNothing_dontCallSecondThenCall_whenAllExpressionAreFalse() {
        IfTest.TestHelper testHelper = mock(IfTest.TestHelper.class);
        If.isTrue(false)
                .thenCall(testHelper::thenCallMe1)
                .elseIf(false)
                .thenCall(testHelper::thenCallMe2)
                .elseDoNothing();
        verify(testHelper, times(0)).thenCallMe2();
    }

    @Test
    public void isTrue_thenThrowElseIfThenCallElseDoNothing_dontCallSecondThenCall_whenAllExpressionAreFalse() {
        IfTest.TestHelper testHelper = mock(IfTest.TestHelper.class);
        If.isTrue(false)
                .thenThrow(() -> new IllegalArgumentException("illegal"))
                .elseIf(false)
                .thenCall(testHelper::thenCallMe2)
                .elseDoNothing();

        verify(testHelper, times(0)).thenCallMe2();
    }

    @Test
    public void isTrue_thenThrowElseIfThenThrowElseDoNothing_dontThrowAnyException_whenAllExpressionAreFalse() {
        If.isTrue(false)
                .thenThrow(() -> new IllegalArgumentException("illegal"))
                .elseIf(false)
                .thenThrow(() -> new RuntimeException("runtime exception"))
                .elseDoNothing();
    }

    @Test
    public void isTrue_thenThrowElseIfThenThrowElseDoNothing_throwThenThrowException_whenExpressionIsTrue() {
        expectedException.expectMessage("illegal");
        expectedException.expect(IllegalArgumentException.class);
        If.isTrue(true)
                .thenThrow(() -> new IllegalArgumentException("illegal"))
                .elseIf(false)
                .thenThrow(() -> new RuntimeException("runtime exception"))
                .elseDoNothing();
    }

    @Test
    public void isTrue_thenThrowElseIfThenThrowElseDoNothing_throwThenThrowException_whenElseIfExpressionIsTrue() {
        expectedException.expectMessage("runtime exception");
        expectedException.expect(RuntimeException.class);
        If.isTrue(false)
                .thenThrow(() -> new IllegalArgumentException("illegal"))
                .elseIf(true)
                .thenThrow(() -> new RuntimeException("runtime exception"))
                .elseDoNothing();
    }

    @Test
    public void isFalse_thenCallElseDoNothing_dontCallThenCall_whenExpressionIsTrue() {
        IfTest.TestHelper testHelper = mock(IfTest.TestHelper.class);
        If.isFalse(true)
                .thenCall(testHelper::thenCallMe1)
                .elseDoNothing();
        verify(testHelper, times(0)).thenCallMe1();
    }

    @Test
    public void isFalse_thenCallElseIfThenCallElseDoNothing_dontCallSecondThenCall() {
        IfTest.TestHelper testHelper = mock(IfTest.TestHelper.class);
        If.isFalse(false)
                .thenCall(testHelper::thenCallMe1)
                .elseIf(false)
                .thenCall(testHelper::thenCallMe2)
                .elseDoNothing();
        verify(testHelper, times(0)).thenCallMe2();
    }

    @Test
    public void isFalse_thenCallElseIfThenCallElseDoNothing_callFirstThenCall() {
        IfTest.TestHelper testHelper = mock(IfTest.TestHelper.class);
        If.isFalse(false)
                .thenCall(testHelper::thenCallMe1)
                .elseIf(false)
                .thenCall(testHelper::thenCallMe2)
                .elseDoNothing();
        verify(testHelper).thenCallMe1();
    }

    @Test
    public void isFalse_thenCallElseIfThenCallElseDoNothing_dontCallFirstThenCall_whenElseIfIsFalse_andExressionIsTrue() {
        IfTest.TestHelper testHelper = mock(IfTest.TestHelper.class);
        If.isFalse(true)
                .thenCall(testHelper::thenCallMe1)
                .elseIf(true)
                .thenCall(testHelper::thenCallMe2)
                .elseDoNothing();
        verify(testHelper, times(0)).thenCallMe1();
    }

    @Test
    public void isFalse_thenCallElseIfThenCallElseDoNothing_callSecondThenCall_whenElseIfIsTrue_andExpressionIsTrue() {
        IfTest.TestHelper testHelper = mock(IfTest.TestHelper.class);
        If.isFalse(true)
                .thenCall(testHelper::thenCallMe1)
                .elseIf(true)
                .thenCall(testHelper::thenCallMe2)
                .elseDoNothing();
        verify(testHelper).thenCallMe2();
    }

    @Test
    public void isFalse_thenCallElseIfThenCallElseDoNothing_dontCallFirstThenCall_whenAllExpressionAreFalseAndIsFalseIsTrue() {
        IfTest.TestHelper testHelper = mock(IfTest.TestHelper.class);
        If.isFalse(true)
                .thenCall(testHelper::thenCallMe1)
                .elseIf(false)
                .thenCall(testHelper::thenCallMe2)
                .elseDoNothing();
        verify(testHelper, times(0)).thenCallMe1();
    }

    @Test
    public void isFalse_thenCallElseIfThenCallElseDoNothing_dontCallSecondThenCall_whenAllExpressionAreFalseAndIsFalseIsTrue() {
        IfTest.TestHelper testHelper = mock(IfTest.TestHelper.class);
        If.isFalse(true)
                .thenCall(testHelper::thenCallMe1)
                .elseIf(false)
                .thenCall(testHelper::thenCallMe2)
                .elseDoNothing();
        verify(testHelper, times(0)).thenCallMe2();
    }

    @Test
    public void isFalse_thenThrowElseIfThenCallElseDoNothing_dontCallSecondThenCall_whenAllExpressionAreFalseAndIsFalseIsTrue() {
        IfTest.TestHelper testHelper = mock(IfTest.TestHelper.class);
        If.isFalse(true)
                .thenThrow(() -> new IllegalArgumentException("illegal"))
                .elseIf(false)
                .thenCall(testHelper::thenCallMe2)
                .elseDoNothing();

        verify(testHelper, times(0)).thenCallMe2();
    }

    @Test
    public void isFalse_thenThrowElseIfThenThrowElseDoNothing_dontThrowAnyException_whenAllExpressionAreFalse() {
        If.isFalse(true)
                .thenThrow(() -> new IllegalArgumentException("illegal"))
                .elseIf(false)
                .thenThrow(() -> new RuntimeException("runtime exception"))
                .elseDoNothing();
    }

    @Test
    public void isFalse_thenThrowElseIfThenThrowElseDoNothing_throwThenThrowException_whenExpressionIsTrue() {
        expectedException.expectMessage("illegal");
        expectedException.expect(IllegalArgumentException.class);
        If.isFalse(false)
                .thenThrow(() -> new IllegalArgumentException("illegal"))
                .elseIf(false)
                .thenThrow(() -> new RuntimeException("runtime exception"))
                .elseDoNothing();
    }

    @Test
    public void isFalse_thenThrowElseIfThenThrowElseDoNothing_throwThenThrowException_whenElseIfExpressionIsTrue() {
        expectedException.expectMessage("runtime exception");
        expectedException.expect(RuntimeException.class);
        If.isFalse(true)
                .thenThrow(() -> new IllegalArgumentException("illegal"))
                .elseIf(true)
                .thenThrow(() -> new RuntimeException("runtime exception"))
                .elseDoNothing();
    }

    @Test
    public void isFalse_thenCallElseDoNothing_callThenCall_whenExpressionIsFalse() {
        IfTest.TestHelper testHelper = mock(IfTest.TestHelper.class);
        If.isFalse(false)
                .thenCall(testHelper::thenCallMe1)
                .elseDoNothing();
        verify(testHelper).thenCallMe1();
    }

    @Test
    public void isFalse_thenCallElseDoNothing_dontCallElseNothing_whenExpressionIsTrue() {
        IfTest.TestHelper testHelper = mock(IfTest.TestHelper.class);
        If.isFalse(true)
                .thenCall(testHelper::thenCallMe1)
                .elseDoNothing();
        verify(testHelper, times(0)).thenCallMe1();
    }

    @Test
    public void isFalse_thenThrowElseDoNothing_throwException_whenExpressionIsFalse() {
        expectedException.expectMessage("runtime");
        expectedException.expect(RuntimeException.class);
        If.isFalse(false)
                .thenThrow(() -> new RuntimeException("runtime"))
                .elseDoNothing();
    }

    @Test
    public void isFalse_thenThrowElseDoNothing_dontThrowException_whenExpressionIsTrue() {
        If.isFalse(true)
                .thenThrow(() -> new RuntimeException("runtime"))
                .elseDoNothing();
    }

    @Test
    public void isTrue_thenThrowElseDoNothing_throwException_whenExpressionIsTrue() {
        expectedException.expectMessage("runtime");
        expectedException.expect(RuntimeException.class);
        If.isTrue(true)
                .thenThrow(() -> new RuntimeException("runtime"))
                .elseDoNothing();
    }

    @Test
    public void isTrue_thenThrowElseDoNothing_dontThrowException_whenExpressionIsFalse() {
        If.isTrue(false)
                .thenThrow(() -> new RuntimeException("runtime"))
                .elseDoNothing();
    }

    static class TestHelper {
        void thenCallMe1() {
            System.out.println("then call me 1");
        }

        void thenCallMe2() {
            System.out.println("then call me2");
        }

        void elseCallMe() {
            System.out.println("else call me");
        }
    }
}

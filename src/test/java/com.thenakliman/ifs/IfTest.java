package com.thenakliman.ifs;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class IfTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void isTrue_thenGetElseGet_returnThenGetValue_whenExpressionIsTrue() {
        Integer value = If.isTrue(true)
                .thenGet(() -> 10)
                .elseGet(() -> 20);

        assertThat(value, is(10));
    }

    @Test
    public void isTrue_thenGetElseIfThenGet_returnFirstThenGetValue() {
        Integer value = If.isTrue(true)
                .thenGet(() -> 10)
                .elseIf(false)
                .thenGet(() -> 20)
                .elseGet(() -> 30);

        assertThat(value, is(10));
    }

    @Test
    public void isTrue_thenGetElseIfThenGet_returnSecondThenGetValue() {
        Integer value = If.isTrue(false)
                .thenGet(() -> 10)
                .elseIf(true)
                .thenGet(() -> 20)
                .elseGet(() -> 30);

        assertThat(value, is(20));
    }

    @Test
    public void isTrue_thenGetElseIfThenGet_returnThirdThenGetValue() {
        Integer value = If.isTrue(false)
                .thenGet(() -> 10)
                .elseIf(false)
                .thenGet(() -> 20)
                .elseIf(true)
                .thenGet(() -> 30)
                .elseGet(() -> 40);

        assertThat(value, is(30));
    }

    @Test
    public void isTrue_thenGetElseIfThenGet_returnElseGetValue() {
        Integer value = If.isTrue(false)
                .thenGet(() -> 10)
                .elseIf(false)
                .thenGet(() -> 20)
                .elseIf(false)
                .thenGet(() -> 30)
                .elseGet(() -> 40);

        assertThat(value, is(40));
    }

    @Test
    public void isTrue_thenValueElseIfThenValue_returnFirstThenValue() {
        Integer value = If.isTrue(true)
                .thenValue(10)
                .elseIf(false)
                .thenValue(20)
                .elseValue(30);

        assertThat(value, is(10));
    }

    @Test
    public void isTrue_thenValueElseIfThenValue_returnSecondThenValue() {
        Integer value = If.isTrue(false)
                .thenValue(10)
                .elseIf(true)
                .thenValue(20)
                .elseValue(30);

        assertThat(value, is(20));
    }

    @Test
    public void isTrue_thenValueElseIfThenValue_returnThirdThenValue() {
        Integer value = If.isTrue(false)
                .thenValue(10)
                .elseIf(false)
                .thenValue(20)
                .elseIf(true)
                .thenValue(30)
                .elseValue(40);

        assertThat(value, is(30));
    }

    @Test
    public void isTrue_thenValueElseIfThenValue_returnElseValue() {
        Integer value = If.isTrue(false)
                .thenValue(10)
                .elseIf(false)
                .thenValue(20)
                .elseIf(false)
                .thenValue(30)
                .elseValue(40);

        assertThat(value, is(40));
    }

    @Test
    public void isTrue_thenValueElseIfThenGet_returnFirstThenValue() {
        Integer value = If.isTrue(true)
                .thenValue(10)
                .elseIf(false)
                .thenGet(() -> 20)
                .elseIf(false)
                .thenValue(30)
                .elseGet(() -> 40);

        assertThat(value, is(10));
    }

    @Test
    public void isTrue_thenValueElseIfThenGet_returnSecondThenValue() {
        Integer value = If.isTrue(false)
                .thenValue(10)
                .elseIf(false)
                .thenGet(() -> 20)
                .elseIf(true)
                .thenValue(30)
                .elseGet(() -> 40);

        assertThat(value, is(30));
    }

    @Test
    public void isTrue_thenValueElseIfThenGet_returnFirstGetValue() {
        Integer value = If.isTrue(false)
                .thenValue(10)
                .elseIf(true)
                .thenGet(() -> 20)
                .elseIf(true)
                .thenValue(30)
                .elseGet(() -> 40);

        assertThat(value, is(20));
    }

    @Test
    public void isTrue_thenValueElseIfThenGet_returnEleGetValue() {
        Integer value = If.isTrue(false)
                .thenValue(10)
                .elseIf(false)
                .thenGet(() -> 20)
                .elseIf(false)
                .thenValue(30)
                .elseGet(() -> 40);

        assertThat(value, is(40));
    }

    @Test
    public void isTrue_thenThrowElseIfThenThrow_throwThenThrow() {
        expectedException.expectMessage("some");
        expectedException.expect(IllegalArgumentException.class);
        If.isTrue(true)
                .thenThrow(() -> new IllegalArgumentException("some"))
                .elseIf(false)
                .thenValue(10)
                .elseValue(20);
    }

    @Test
    public void isTrue_thenThrowElseIfThenValueElseValue_returnThenValue() {
        Integer some = If.isTrue(false)
                .thenThrow(() -> new IllegalArgumentException("some"))
                .elseIf(true)
                .thenValue(10)
                .elseValue(20);

        assertThat(some, is(10));
    }

    @Test
    public void isTrue_thenThrowElseIfThenValueElseValue_returnElseValue() {
        Integer some = If.isTrue(false)
                .thenThrow(() -> new IllegalArgumentException("some"))
                .elseIf(false)
                .thenValue(10)
                .elseValue(20);

        assertThat(some, is(20));
    }

    @Test
    public void isTrue_thenThrowElseIfThenValueThenValueElseValue_returnSecondThenValue() {
        Integer some = If.isTrue(false)
                .thenThrow(() -> new IllegalArgumentException("some"))
                .elseIf(false)
                .thenValue(10)
                .elseIf(true)
                .thenValue(20)
                .elseValue(30);

        assertThat(some, is(20));
    }

    @Test
    public void isTrue_thenThrowElseIfThenValueThenValueElseValue_returnElseValue() {
        Integer some = If.isTrue(false)
                .thenThrow(() -> new IllegalArgumentException("some"))
                .elseIf(false)
                .thenValue(10)
                .elseIf(false)
                .thenValue(20)
                .elseValue(30);

        assertThat(some, is(30));
    }

    @Test
    public void isTrue_thenThrowElseIfThenValueThenValueThenValueElseValue_return3rdThenValue() {
        Integer some = If.isTrue(false)
                .thenThrow(() -> new IllegalArgumentException("some"))
                .elseIf(false)
                .thenValue(10)
                .elseIf(false)
                .thenValue(20)
                .elseIf(true)
                .thenValue(30)
                .elseValue(40);

        assertThat(some, is(30));
    }

    @Test
    public void isTrue_thenThrowElseIfThenValueThenValueThenValueElseValue_returnElseValue() {
        Integer some = If.isTrue(false)
                .thenThrow(() -> new IllegalArgumentException("some"))
                .elseIf(false)
                .thenValue(10)
                .elseIf(false)
                .thenValue(20)
                .elseIf(false)
                .thenValue(30)
                .elseValue(40);

        assertThat(some, is(40));
    }

    @Test
    public void isTrue_thenThrowElseIfThenValueThenValueThenValueElseValue_throwException() {
        expectedException.expectMessage("some");
        expectedException.expect(IllegalArgumentException.class);
        If.isTrue(true)
                .thenThrow(() -> new IllegalArgumentException("some"))
                .elseIf(false)
                .thenValue(10)
                .elseIf(false)
                .thenValue(20)
                .elseIf(true)
                .thenValue(30)
                .elseValue(40);
    }

    @Test
    public void isTrue_thenValueElseIfThrowThenValueThenValueElseValue_throwException() {
        expectedException.expectMessage("some");
        expectedException.expect(IllegalArgumentException.class);
        If.isTrue(false)
                .thenValue(10)
                .elseIf(true)
                .thenThrow(() -> new IllegalArgumentException("some"))
                .elseIf(false)
                .thenValue(20)
                .elseIf(true)
                .thenValue(30)
                .elseValue(40);
    }

    @Test
    public void isTrue_thenValueThenValueElseIfThrowThenValueElseValue_throwException() {
        expectedException.expectMessage("some");
        expectedException.expect(IllegalArgumentException.class);
        If.isTrue(false)
                .thenValue(10)
                .elseIf(false)
                .thenValue(20)
                .elseIf(true)
                .thenThrow(() -> new IllegalArgumentException("some"))
                .elseIf(true)
                .thenValue(30)
                .elseValue(40);
    }

    @Test
    public void isTrue_thenValueThenValueThenValueElseIfThrowElseValue_throwException() {
        expectedException.expectMessage("some");
        expectedException.expect(IllegalArgumentException.class);
        If.isTrue(false)
                .thenValue(10)
                .elseIf(false)
                .thenValue(20)
                .elseIf(false)
                .thenValue(30)
                .elseIf(true)
                .thenThrow(() -> new IllegalArgumentException("some"))
                .elseValue(40);
    }

    @Test
    public void isTrue_thenValueThenValueThenValueThenValueElseThrow_throwException() {
        expectedException.expectMessage("some");
        expectedException.expect(IllegalArgumentException.class);
        If.isTrue(false)
                .thenValue(10)
                .elseIf(false)
                .thenValue(20)
                .elseIf(false)
                .thenValue(30)
                .elseIf(false)
                .thenValue(40)
                .elseThrow(() -> new IllegalArgumentException("some"));
    }

    @Test
    public void isTrue_thenGetThenGetThenGetThenGetElseThrow_throwException() {
        expectedException.expectMessage("some");
        expectedException.expect(IllegalArgumentException.class);
        If.isTrue(false)
                .thenGet(() -> 10)
                .elseIf(false)
                .thenGet(() -> 20)
                .elseIf(false)
                .thenGet(() -> 30)
                .elseIf(false)
                .thenGet(() -> 40)
                .elseThrow(() -> new IllegalArgumentException("some"));
    }

    @Test
    public void isTrue_thenThrowThenGetThenGetThenGetElseGet_throwException() {
        expectedException.expectMessage("some");
        expectedException.expect(IllegalArgumentException.class);
        If.isTrue(true)
                .thenThrow(() -> new IllegalArgumentException("some"))
                .elseIf(false)
                .thenGet(() -> 20)
                .elseIf(true)
                .thenGet(() -> 30)
                .elseIf(true)
                .thenGet(() -> 40)
                .elseGet(() -> 50);
    }

    @Test
    public void isTrue_thenGetThenGetThenThrowThenGetElseGet_throwException() {
        expectedException.expectMessage("some");
        expectedException.expect(IllegalArgumentException.class);
        If.isTrue(false)
                .thenGet(() -> 10)
                .elseIf(false)
                .thenGet(() -> 20)
                .elseIf(true)
                .thenThrow(() -> new IllegalArgumentException("some"))
                .elseIf(true)
                .thenGet(() -> 40)
                .elseGet(() -> 50);
    }

    @Test
    public void isTrue_thenGetThenGetThenThrowThenGetElseThrow_throwException() {
        expectedException.expectMessage("0000");
        expectedException.expect(RuntimeException.class);
        If.isTrue(false)
                .thenGet(() -> 10)
                .elseIf(false)
                .thenGet(() -> 20)
                .elseIf(false)
                .thenThrow(() -> new IllegalArgumentException("some"))
                .elseIf(false)
                .thenGet(() -> 40)
                .elseThrow(() -> new RuntimeException("0000"));
    }

    @Test
    public void isTrue_thenThrowThenGetThenThrowThenGetElseThrow_throwException() {
        expectedException.expectMessage("11111");
        expectedException.expect(IllegalAccessError.class);
        If.isTrue(true)
                .thenThrow(() -> new IllegalAccessError("11111"))
                .elseIf(true)
                .thenGet(() -> 20)
                .elseIf(true)
                .thenThrow(() -> new IllegalArgumentException("some"))
                .elseIf(true)
                .thenGet(() -> 40)
                .elseThrow(() -> new RuntimeException("0000"));
    }

    @Test
    public void isTrue_thenThrowThenThrowThenThrowElseThrow_throwFirstException() {
        expectedException.expectMessage("11111");
        expectedException.expect(IllegalAccessError.class);
        If.isTrue(true)
                .thenThrow(() -> new IllegalAccessError("11111"))
                .elseIf(true)
                .thenThrow(() -> new IllegalCallerException("22222"))
                .elseIf(true)
                .thenThrow(() -> new IllegalArgumentException("some"))
                .elseIf(true)
                .thenThrow(() -> new IllegalMonitorStateException("123423"))
                .elseThrow(() -> new RuntimeException("0000"));
    }

    @Test
    public void isTrue_thenThrowThenThrowThenThrowElseThrow_throwSecondException() {
        expectedException.expectMessage("22222");
        expectedException.expect(IllegalCallerException.class);
        If.isTrue(false)
                .thenThrow(() -> new IllegalAccessError("11111"))
                .elseIf(true)
                .thenThrow(() -> new IllegalCallerException("22222"))
                .elseIf(true)
                .thenThrow(() -> new IllegalArgumentException("some"))
                .elseIf(true)
                .thenThrow(() -> new IllegalMonitorStateException("123423"))
                .elseThrow(() -> new RuntimeException("0000"));
    }

    @Test
    public void isTrue_thenThrowThenThrowThenThrowElseThrow_throwThirdException() {
        expectedException.expectMessage("some");
        expectedException.expect(IllegalArgumentException.class);
        If.isTrue(false)
                .thenThrow(() -> new IllegalAccessError("11111"))
                .elseIf(false)
                .thenThrow(() -> new IllegalCallerException("22222"))
                .elseIf(true)
                .thenThrow(() -> new IllegalArgumentException("some"))
                .elseIf(true)
                .thenThrow(() -> new IllegalMonitorStateException("123423"))
                .elseThrow(() -> new RuntimeException("0000"));
    }

    @Test
    public void isTrue_thenThrowThenThrowThenThrowElseThrow_throwFourthException() {
        expectedException.expectMessage("123423");
        expectedException.expect(IllegalMonitorStateException.class);
        If.isTrue(false)
                .thenThrow(() -> new IllegalAccessError("11111"))
                .elseIf(false)
                .thenThrow(() -> new IllegalCallerException("22222"))
                .elseIf(false)
                .thenThrow(() -> new IllegalArgumentException("some"))
                .elseIf(true)
                .thenThrow(() -> new IllegalMonitorStateException("123423"))
                .elseThrow(() -> new RuntimeException("0000"));
    }

    @Test
    public void isTrue_thenThrowThenThrowThenThrowElseThrow_throwElseException() {
        expectedException.expectMessage("0000");
        expectedException.expect(RuntimeException.class);
        If.isTrue(false)
                .thenThrow(() -> new IllegalAccessError("11111"))
                .elseIf(false)
                .thenThrow(() -> new IllegalCallerException("22222"))
                .elseIf(false)
                .thenThrow(() -> new IllegalArgumentException("some"))
                .elseIf(false)
                .thenThrow(() -> new IllegalMonitorStateException("123423"))
                .elseThrow(() -> new RuntimeException("0000"));
    }

    @Test
    public void isTrue_thenThrowThenThrowThenValueElseThrow_throwElseException() {
        expectedException.expectMessage("0000");
        expectedException.expect(RuntimeException.class);
        If.isTrue(false)
                .thenThrow(() -> new IllegalAccessError("11111"))
                .elseIf(false)
                .thenThrow(() -> new IllegalCallerException("22222"))
                .elseIf(false)
                .thenThrow(() -> new IllegalArgumentException("some"))
                .elseIf(false)
                .thenValue(10)
                .elseThrow(() -> new RuntimeException("0000"));
    }

    @Test
    public void isTrue_thenThrowThenThrowThenValueElseThrow_returnThenValue() {
        Integer some = If.isTrue(false)
                .thenThrow(() -> new IllegalAccessError("11111"))
                .elseIf(false)
                .thenThrow(() -> new IllegalCallerException("22222"))
                .elseIf(false)
                .thenThrow(() -> new IllegalArgumentException("some"))
                .elseIf(true)
                .thenValue(10)
                .elseThrow(() -> new RuntimeException("0000"));
        assertThat(some, is(10));
    }

    @Test
    public void isTrue_thenThrowThenThrowThenValueElseThrow_throwThirdException() {
        expectedException.expectMessage("some");
        expectedException.expect(IllegalArgumentException.class);
        If.isTrue(false)
                .thenThrow(() -> new IllegalAccessError("11111"))
                .elseIf(false)
                .thenThrow(() -> new IllegalCallerException("22222"))
                .elseIf(true)
                .thenThrow(() -> new IllegalArgumentException("some"))
                .elseIf(false)
                .thenValue(10)
                .elseThrow(() -> new RuntimeException("0000"));
    }

    @Test
    public void isTrue_thenThrowThenThrowThenValueElseThrow_throwSecondException() {
        expectedException.expectMessage("22222");
        expectedException.expect(IllegalCallerException.class);
        If.isTrue(false)
                .thenThrow(() -> new IllegalAccessError("11111"))
                .elseIf(true)
                .thenThrow(() -> new IllegalCallerException("22222"))
                .elseIf(false)
                .thenThrow(() -> new IllegalArgumentException("some"))
                .elseIf(false)
                .thenValue(10)
                .elseThrow(() -> new RuntimeException("0000"));
    }

    @Test
    public void isTrue_thenThrowThenThrowThenGetElseThrow_returnThenGet() {
        Integer some = If.isTrue(false)
                .thenThrow(() -> new IllegalAccessError("11111"))
                .elseIf(false)
                .thenThrow(() -> new IllegalCallerException("22222"))
                .elseIf(false)
                .thenThrow(() -> new IllegalArgumentException("some"))
                .elseIf(true)
                .thenGet(() -> 10)
                .elseThrow(() -> new RuntimeException("0000"));

        assertThat(some, is(10));
    }

    @Test
    public void isTrue_thenThrowThenThrowThenGetElseGet_returnElseGet() {
        Integer some = If.isTrue(false)
                .thenThrow(() -> new IllegalAccessError("11111"))
                .elseIf(false)
                .thenThrow(() -> new IllegalCallerException("22222"))
                .elseIf(false)
                .thenThrow(() -> new IllegalArgumentException("some"))
                .elseIf(false)
                .thenGet(() -> 10)
                .elseGet(() -> 20);

        assertThat(some, is(20));
    }

    @Test
    public void isTrue_thenThrowThenThrowThenValueElseThrow_throwFirstException() {
        expectedException.expectMessage("11111");
        expectedException.expect(IllegalAccessError.class);
        If.isTrue(true)
                .thenThrow(() -> new IllegalAccessError("11111"))
                .elseIf(false)
                .thenThrow(() -> new IllegalCallerException("22222"))
                .elseIf(false)
                .thenThrow(() -> new IllegalArgumentException("some"))
                .elseIf(false)
                .thenValue(10)
                .elseThrow(() -> new RuntimeException("0000"));
    }

    @Test
    public void isTrue_thenGetThenGetThenGetElseGet_returnFirstGet() {
        Integer value = If.isTrue(true)
                .thenGet(() -> 10)
                .elseIf(false)
                .thenGet(() -> 20)
                .elseIf(false)
                .thenGet(() -> 30)
                .elseIf(false)
                .thenGet(() -> 40)
                .elseGet(() -> 50);

        assertThat(value, is(10));
    }

    @Test
    public void isTrue_thenGetThenGetThenGetElseGet_returnSecondGet() {
        Integer value = If.isTrue(false)
                .thenGet(() -> 10)
                .elseIf(true)
                .thenGet(() -> 20)
                .elseIf(false)
                .thenGet(() -> 30)
                .elseIf(true)
                .thenGet(() -> 40)
                .elseGet(() -> 50);

        assertThat(value, is(20));
    }

    @Test
    public void isTrue_thenGetThenGetThenGetElseGet_returnThirdGet() {
        Integer value = If.isTrue(false)
                .thenGet(() -> 10)
                .elseIf(false)
                .thenGet(() -> 20)
                .elseIf(true)
                .thenGet(() -> 30)
                .elseIf(true)
                .thenGet(() -> 40)
                .elseGet(() -> 50);

        assertThat(value, is(30));
    }

    @Test
    public void isTrue_thenGetThenGetThenGetElseGet_returnFourthGet() {
        Integer value = If.isTrue(false)
                .thenGet(() -> 10)
                .elseIf(false)
                .thenGet(() -> 20)
                .elseIf(false)
                .thenGet(() -> 30)
                .elseIf(true)
                .thenGet(() -> 40)
                .elseGet(() -> 50);

        assertThat(value, is(40));
    }

    @Test
    public void isTrue_thenGetThenGetThenGetElseGet_returnFifthGet() {
        Integer value = If.isTrue(false)
                .thenGet(() -> 10)
                .elseIf(false)
                .thenGet(() -> 20)
                .elseIf(false)
                .thenGet(() -> 30)
                .elseIf(false)
                .thenGet(() -> 40)
                .elseGet(() -> 50);

        assertThat(value, is(50));
    }

    @Test
    public void isTrue_thenThrowThenThrowElseValue_returnElseValue() {
        Integer value = If.isTrue(false)
                .thenThrow(() -> new RuntimeException("run time"))
                .elseIf(false)
                .thenThrow(() -> new IllegalArgumentException("illegal"))
                .elseValue(50);

        assertThat(value, is(50));
    }

    @Test
    public void isTrue_thenThrowThenThrowElseValue_returnElseGet() {
        Integer value = If.isTrue(false)
                .thenThrow(() -> new RuntimeException("run time"))
                .elseIf(false)
                .thenThrow(() -> new IllegalArgumentException("illegal"))
                .elseGet(() -> 50);

        assertThat(value, is(50));
    }

    @Test
    public void isTrue_thenThrowThenGetThenValueThrowElseValue_returnThenGet() {
        Integer value = If.isTrue(false)
                .thenThrow(() -> new RuntimeException("run time"))
                .elseIf(true)
                .thenGet(() -> 10)
                .elseIf(true)
                .thenValue(20)
                .elseIf(true)
                .thenThrow(() -> new IllegalArgumentException("illegal"))
                .elseGet(() -> 50);

        assertThat(value, is(10));
    }

    @Test
    public void isTrue_thenThrowThenGetThenValueThrowElseValue_returnThenValue() {
        Integer value = If.isTrue(false)
                .thenThrow(() -> new RuntimeException("run time"))
                .elseIf(false)
                .thenGet(() -> 10)
                .elseIf(true)
                .thenValue(20)
                .elseIf(true)
                .thenThrow(() -> new IllegalArgumentException("illegal"))
                .elseGet(() -> 50);

        assertThat(value, is(20));
    }

    @Test
    public void isTrue_thenThrowThenGetThenValueThrowElseValue_returnElseValue() {
        Integer value = If.isTrue(false)
                .thenThrow(() -> new RuntimeException("run time"))
                .elseIf(false)
                .thenGet(() -> 10)
                .elseIf(false)
                .thenValue(20)
                .elseIf(false)
                .thenThrow(() -> new IllegalArgumentException("illegal"))
                .elseGet(() -> 50);

        assertThat(value, is(50));
    }

    @Test
    public void isTrue_thenThrowThenGetThenValueThrowElseValue_throwFirstException() {
        expectedException.expectMessage("run time");
        expectedException.expect(RuntimeException.class);
        If.isTrue(true)
                .thenThrow(() -> new RuntimeException("run time"))
                .elseIf(true)
                .thenGet(() -> 10)
                .elseIf(true)
                .thenValue(20)
                .elseIf(true)
                .thenThrow(() -> new IllegalArgumentException("illegal"))
                .elseGet(() -> 50);
    }

    @Test
    public void isTrue_thenThrowThenGetThenValueThrowElseValue_throwSecondException() {
        expectedException.expectMessage("illegal");
        expectedException.expect(IllegalArgumentException.class);
        If.isTrue(false)
                .thenThrow(() -> new RuntimeException("run time"))
                .elseIf(false)
                .thenGet(() -> 10)
                .elseIf(false)
                .thenValue(20)
                .elseIf(true)
                .thenThrow(() -> new IllegalArgumentException("illegal"))
                .elseGet(() -> 50);
    }

    @Test
    public void isTrue_thenGetElseGet_returnElseGetValue_whenExpressionIsFalse() {
        Integer value = If.isTrue(false)
                .thenGet(() -> 10)
                .elseGet(() -> 20);

        assertThat(value, is(20));
    }

    @Test
    public void isTrue_thenThrowElseCall_throwThenThrowException_whenExpressionIsTrue() {
        TestHelper testHelper = mock(TestHelper.class);

        expectedException.expect(IllegalArgumentException.class);
        If.isTrue(true)
                .thenThrow(IllegalArgumentException::new)
                .elseCall(testHelper::elseCallMe);
    }

    @Test
    public void isTrue_ThenThrowElseCall_callThenCall_whenExpressionIsFalse() {
        TestHelper testHelper = mock(TestHelper.class);

        If.isTrue(false)
                .thenThrow(IllegalArgumentException::new)
                .elseCall(testHelper::elseCallMe);
        verify(testHelper).elseCallMe();
    }

    @Test
    public void isTrue_thenThrowElseGet_returnElseGetValue_whenExpressionIsFalse() {
        final Integer value = If.isTrue(false)
                .thenThrow(IllegalArgumentException::new)
                .elseGet(() -> 20);
        assertThat(value, is(20));
    }

    @Test
    public void isTrue_thenThrowElseGet_throwThenThrowException_whenExpressionIsTrue() {
        expectedException.expect(IllegalArgumentException.class);
        If.isTrue(true)
                .thenThrow(IllegalArgumentException::new)
                .elseGet(() -> 20);
    }

    @Test
    public void isTrue_thenThrowElseThrow_throwElseThrowException_whenExpressionIsTrue() {
        expectedException.expect(IllegalArgumentException.class);
        If.isTrue(true)
                .thenThrow(IllegalArgumentException::new)
                .elseThrow(RuntimeException::new);
    }

    @Test
    public void isTrue_thenThrowElseThrow_throwElseThrowException_whenExpressionIsFalse() {
        expectedException.expect(RuntimeException.class);
        If.isTrue(false)
                .thenThrow(IllegalArgumentException::new)
                .elseThrow(RuntimeException::new);
    }

    @Test
    public void isTrue_thenGetElseThrow_returnThenGet_whenExpressionIsTrue() {
        final Integer value = If.isTrue(true)
                .thenGet(() -> 34)
                .elseThrow(IllegalArgumentException::new);
        assertThat(value, is(34));
    }

    @Test
    public void isTrue_thenValueElseValue_returnThenValue_whenExpressionIsTrue() {
        final Integer value = If.isTrue(true)
                .thenValue(30)
                .elseValue(31);
        assertThat(value, is(30));
    }

    @Test
    public void isTrue_thenValueElseValue_returnElseValue_whenExpressionIsFalse() {
        final Integer value = If.isTrue(false)
                .thenValue(30)
                .elseValue(31);
        assertThat(value, is(31));
    }

    @Test
    public void isTrue_thenValueElseGet_returnThenValue_whenExpressionIsTrue() {
        final Integer value = If.isTrue(true)
                .thenValue(30)
                .elseGet(() -> 31);
        assertThat(value, is(30));
    }

    @Test
    public void isTrue_thenValueElseGet_returnElseValue_whenExpressionIsFalse() {
        final Integer value = If.isTrue(false)
                .thenValue(30)
                .elseGet(() -> 31);
        assertThat(value, is(31));
    }

    @Test
    public void isTrue_thenValueElseThrow_returnThenValue_whenExpressionIsTrue() {
        final Integer value = If.isTrue(true)
                .thenValue(30)
                .elseThrow(IllegalArgumentException::new);
        assertThat(value, is(30));
    }

    @Test
    public void isTrue_thenValueElseThrow_throwElseThrowException_whenExpressionIsFalse() {
        expectedException.expect(IllegalArgumentException.class);
        If.isTrue(false)
                .thenValue(30)
                .elseThrow(IllegalArgumentException::new);
    }

    @Test
    public void isTrue_thenGetElseValue_returnElseValue_whenExpressionIsFalse() {
        final Integer value = If.isTrue(false)
                .thenGet(() -> 30)
                .elseValue(31);
        assertThat(value, is(31));
    }

    @Test
    public void isTrue_thenGetElseValue_returnThenGetValue_whenExpressionIsTrue() {
        final Integer value = If.isTrue(true)
                .thenGet(() -> 30)
                .elseValue(31);
        assertThat(value, is(30));
    }

    @Test
    public void isTrue_thenThrowElseValue_throwThenThrowException_whenExpressionIsTrue() {
        expectedException.expect(IllegalArgumentException.class);
        If.isTrue(true)
                .thenThrow(IllegalArgumentException::new)
                .elseValue(31);
    }

    @Test
    public void isTrue_thenThrowElseValue_returnElseValue_whenExpressionIsFalse() {
        final Integer value = If.isTrue(false)
                .thenThrow(IllegalArgumentException::new)
                .elseValue(31);

        assertThat(value, is(31));
    }

    @Test
    public void isFalse_thenValueElseValue_returnThenValue_whenExpressionIsFalse() {
        final Integer value = If.isFalse(false)
                .thenValue(30)
                .elseValue(31);
        assertThat(value, is(30));
    }

    @Test
    public void isFalse_thenValueElseValue_returnElseValue_whenExpressionIsTrue() {
        final Integer value = If.isFalse(true)
                .thenValue(30)
                .elseValue(31);
        assertThat(value, is(31));
    }

    @Test
    public void isFalse_thenValueElseGet_returnThenValue_whenExpressionIsFalse() {
        final Integer value = If.isFalse(false)
                .thenValue(30)
                .elseGet(() -> 31);
        assertThat(value, is(30));
    }

    @Test
    public void isFalsee_thenValueElseGet_returnElseValue_whenExpressionIsTrue() {
        final Integer value = If.isFalse(true)
                .thenValue(30)
                .elseGet(() -> 31);
        assertThat(value, is(31));
    }

    @Test
    public void isFalse_thenValueElseThrow_returnThenValue_whenExpressionIsFalse() {
        final Integer value = If.isFalse(false)
                .thenValue(30)
                .elseThrow(IllegalArgumentException::new);
        assertThat(value, is(30));
    }

    @Test
    public void isFalse_thenValueElseThrow_throwElseThrowException_whenExpressionIsTrue() {
        expectedException.expect(IllegalArgumentException.class);
        If.isFalse(true)
                .thenValue(30)
                .elseThrow(IllegalArgumentException::new);
    }

    @Test
    public void isFalse_thenGetElseValue_returnElseValue_whenExpressionIsTrue() {
        final Integer value = If.isFalse(true)
                .thenGet(() -> 30)
                .elseValue(31);
        assertThat(value, is(31));
    }

    @Test
    public void isFalse_thenGetElseValue_returnThenGetValue_whenExpressionIsFalse() {
        final Integer value = If.isFalse(false)
                .thenGet(() -> 30)
                .elseValue(31);
        assertThat(value, is(30));
    }

    @Test
    public void isFalse_thenThrowElseValue_throwThenThrowException_whenExpressionIsFalse() {
        expectedException.expect(IllegalArgumentException.class);
        If.isFalse(false)
                .thenThrow(IllegalArgumentException::new)
                .elseValue(31);
    }

    @Test
    public void isFalse_thenThrowElseValue_returnElseValue_whenExpressionIsTrue() {
        final Integer value = If.isFalse(true)
                .thenThrow(IllegalArgumentException::new)
                .elseValue(31);

        assertThat(value, is(31));
    }

    @Test
    public void isTrue_thenGetElseThrow_throwElseThrowException_whenExpressionIsFalse() {
        expectedException.expect(IllegalArgumentException.class);
        If.isTrue(false)
                .thenGet(() -> 34)
                .elseThrow(IllegalArgumentException::new);
    }

    @Test
    public void isFalse_thenGetElseGet_returnThenGetValue_whenExpressionIsFalse() {
        Integer value = If.isFalse(true)
                .thenGet(() -> 10)
                .elseGet(() -> 20);

        assertThat(value, is(20));
    }

    @Test
    public void isFalse_thenGetElseGet_returnElseGetValue_whenExpressionIsTrue() {
        Integer value = If.isFalse(true)
                .thenGet(() -> 10)
                .elseGet(() -> 20);

        assertThat(value, is(20));
    }

    @Test
    public void isFalse_thenThrowElseCall_throwThenThrowException_whenExpressionIsFalse() {
        TestHelper testHelper = mock(TestHelper.class);

        expectedException.expect(IllegalArgumentException.class);
        If.isFalse(false)
                .thenThrow(IllegalArgumentException::new)
                .elseCall(testHelper::elseCallMe);
    }

    @Test
    public void isFalse_thenThrowElseCall_callElseCall_whenExpressionIsTrue() {
        TestHelper testHelper = mock(TestHelper.class);

        If.isFalse(true)
                .thenThrow(IllegalArgumentException::new)
                .elseCall(testHelper::elseCallMe);
        verify(testHelper).elseCallMe();
    }

    @Test
    public void isFalse_thenThrowElseGet_returnElseGetValue_whenExpressionIsTrue() {
        final Integer value = If.isFalse(true)
                .thenThrow(IllegalArgumentException::new)
                .elseGet(() -> 20);
        assertThat(value, is(20));
    }

    @Test
    public void isFalse_thenThrowElseGet_throwThenThrowException_whenExpressionIsFalse() {
        expectedException.expect(IllegalArgumentException.class);
        If.isFalse(false)
                .thenThrow(IllegalArgumentException::new)
                .elseGet(() -> 20);
    }

    @Test
    public void isFalse_thenThrowElseThrow_throwThenThrowException_whenExpressionIsFalse() {
        expectedException.expect(IllegalArgumentException.class);
        If.isFalse(false)
                .thenThrow(IllegalArgumentException::new)
                .elseThrow(RuntimeException::new);
    }

    @Test
    public void isFalse_thenThrowElseThrow_throwElseThrowException_whenExpressionIsTrue() {
        expectedException.expect(RuntimeException.class);
        If.isFalse(true)
                .thenThrow(IllegalArgumentException::new)
                .elseThrow(RuntimeException::new);
    }

    @Test
    public void isFalse_thenGetElseThrow_returnGetValue_whenExpressionIsFalse() {
        final Integer value = If.isFalse(false)
                .thenGet(() -> 34)
                .elseThrow(IllegalArgumentException::new);
        assertThat(value, is(34));
    }

    @Test
    public void isFalse_thenGetElseThrow_throwElseThrow_whenExpressionIsTrue() {
        expectedException.expect(IllegalArgumentException.class);
        If.isFalse(true)
                .thenGet(() -> 34)
                .elseThrow(IllegalArgumentException::new);
    }

    @Test
    public void isFalseThen_thenThrow_throwThenThrowException_whenExpressionIsTrue() {
        expectedException.expect(IllegalArgumentException.class);
        If.isFalseThen(false).thenThrow(IllegalArgumentException::new);
    }

    @Test
    public void isFalseThen_thenThrow__doNotThrowException_whenExpressionIsFalse() {
        If.isFalseThen(true).thenThrow(IllegalArgumentException::new);
    }

    static class TestHelper {
        void thenCallMe1() {
            System.out.println("then call me1");
        }

        void thenCallMe2() {
            System.out.println("then call me2");
        }

        void elseCallMe() {
            System.out.println("else call me");
        }
    }
}

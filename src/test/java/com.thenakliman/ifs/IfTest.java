package com.thenakliman.ifs;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class IfTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private GetTestHelper testHelper = mock(GetTestHelper.class);


    @Test
    public void isTrue_thenGetElseGet_returnThenGetValue_whenExpressionIsTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        Integer value = If.isTrue(true)
                .thenGet(testHelper::thenGet1)
                .elseGet(testHelper::elseGet);

        assertThat(value, is(10));
    }

    @Test
    public void isTrue_thenGetElseGet_returnThenGetValueElseGetIsNotCalled_whenExpressionIsTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        If.isTrue(true)
                .thenGet(testHelper::thenGet1)
                .elseGet(testHelper::elseGet);

        verify(testHelper, times(0)).thenGet2();
    }

    @Test
    public void isTrue_thenGetElseGet_returnThenElseGetValue_whenExpressionIsFalse() {
        when(testHelper.elseGet()).thenReturn(20);
        Integer value = If.isTrue(false)
                .thenGet(testHelper::thenGet1)
                .elseGet(testHelper::elseGet);

        assertThat(value, is(20));
    }

    @Test
    public void isTrue_thenGetElseGet_returnElseGetValueThenGetIsNotCalled_whenExpressionIsFalse() {
        when(testHelper.elseGet()).thenReturn(20);
        If.isTrue(false)
                .thenGet(testHelper::thenGet1)
                .elseGet(testHelper::elseGet);

        verify(testHelper, times(0)).thenGet1();
    }

    @Test
    public void isTrue_thenThrowElseGet_returnElseGetValue_whenExpressionIsFalse() {
        when(testHelper.elseGet()).thenReturn(20);
        final Integer value = If.isTrue(false)
                .thenThrow(IllegalArgumentException::new)
                .elseGet(testHelper::elseGet);
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
    public void isTrue_thenThrowElseGet_throwThenThrowExceptionElseGetIsNotCalled_whenExpressionIsTrue() {
        when(testHelper.elseGet()).thenReturn(20);
        try {
            If.isTrue(true)
                    .thenThrow(IllegalArgumentException::new)
                    .elseGet(() -> 20);
            fail("Illegal argument exception is expected");
        } catch (IllegalArgumentException exception) {
            verify(testHelper, times(0)).elseGet();
        }
    }

    @Test
    public void isTrue_thenThrowElseThrow_throwElseThrowException_whenExpressionIsTrue() {
        expectedException.expect(IllegalArgumentException.class);
        If.isTrue(true)
                .thenThrow(IllegalArgumentException::new)
                .elseThrow(RuntimeException::new);
    }

    @Test
    public void isTrue_thenThrowElseThrow_throwElseThrowException_whenExpressionIsFalse() throws Exception {
        expectedException.expect(Exception.class);
        If.isTrue(false)
                .thenThrow(IllegalArgumentException::new)
                .elseThrow(Exception::new);
    }

    @Test
    public void isTrue_thenGetElseThrow_returnThenGet_whenExpressionIsTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        final Integer value = If.isTrue(true)
                .thenGet(testHelper::thenGet1)
                .elseThrow(IllegalArgumentException::new);
        assertThat(value, is(10));
    }

    @Test
    public void isTrue_thenGetElseThrow_throwElseThrowException_whenExpressionIsFalse() {
        expectedException.expect(IllegalArgumentException.class);
        If.isTrue(false)
                .thenGet(() -> 34)
                .elseThrow(IllegalArgumentException::new);
    }

    @Test
    public void isTrue_thenGetElseThrow_throwElseThrowExceptionThenGetIsNotCalled_whenExpressionIsFalse() {
        try {
            If.isTrue(false)
                    .thenGet(testHelper::thenGet1)
                    .elseThrow(IllegalArgumentException::new);
        } catch (IllegalArgumentException exception) {
            verify(testHelper, times(0)).thenGet1();
        }
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
    public void isTrue_thenValueElseGet_returnThenValueElseGetIsNotCalled_whenExpressionIsTrue() {
        when(testHelper.elseGet()).thenReturn(31);
        final Integer value = If.isTrue(true)
                .thenValue(30)
                .elseGet(testHelper::elseGet);
        verify(testHelper, times(0)).elseGet();
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
    public void isTrue_thenGetElseValue_returnElseValueThenGetIsNotCalled_whenExpressionIsFalse() {
        when(testHelper.thenGet1()).thenReturn(30);
        final Integer value = If.isTrue(false)
                .thenGet(() -> 30)
                .elseValue(31);
        verify(testHelper, times(0)).thenGet1();
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
    public void isTrue_thenGetElseIfThenGetElseGet_returnFirstThenGetValue() {
        when(testHelper.thenGet1()).thenReturn(10);
        when(testHelper.thenGet2()).thenReturn(20);
        when(testHelper.elseGet()).thenReturn(30);
        Integer value = If.isTrue(true)
                .thenGet(testHelper::thenGet1)
                .elseIf(false)
                .thenGet(testHelper::thenGet2)
                .elseGet(testHelper::elseGet);

        assertThat(value, is(10));
    }

    @Test
    public void isTrue_thenGetElseIfThenGetElseGet_returnFirstThenGetValueSecondThenGetIsNotCalled() {
        when(testHelper.thenGet1()).thenReturn(10);
        when(testHelper.thenGet2()).thenReturn(20);
        when(testHelper.elseGet()).thenReturn(30);
        If.isTrue(true)
                .thenGet(testHelper::thenGet1)
                .elseIf(false)
                .thenGet(testHelper::thenGet2)
                .elseGet(testHelper::elseGet);

        verify(testHelper, times(0)).thenGet2();
    }

    @Test
    public void isTrue_thenGetElseIfThenGetElseGet_returnFirstThenGetValueSecondElseGetIsNotCalled() {
        when(testHelper.thenGet1()).thenReturn(10);
        when(testHelper.thenGet2()).thenReturn(20);
        when(testHelper.elseGet()).thenReturn(30);
        If.isTrue(true)
                .thenGet(testHelper::thenGet1)
                .elseIf(false)
                .thenGet(testHelper::thenGet2)
                .elseGet(testHelper::elseGet);

        verify(testHelper, times(0)).elseGet();
    }

    @Test
    public void isTrue_thenGetElseIfThenGetElseGet_returnFirstThenGetValueSecondElseGetIsNotCalled_whenSecondExpressionIsAlsoTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        when(testHelper.thenGet2()).thenReturn(20);
        when(testHelper.elseGet()).thenReturn(30);
        If.isTrue(true)
                .thenGet(testHelper::thenGet1)
                .elseIf(true)
                .thenGet(testHelper::thenGet2)
                .elseGet(testHelper::elseGet);

        verify(testHelper, times(0)).thenGet2();
    }

    @Test
    public void isTrue_thenGetElseIfThenGetElseGet_returnSecondThenGetValue_whenFirstExpressionIsFalse_andSecondIsTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        when(testHelper.thenGet2()).thenReturn(20);
        when(testHelper.elseGet()).thenReturn(30);
        Integer value = If.isTrue(false)
                .thenGet(testHelper::thenGet1)
                .elseIf(true)
                .thenGet(testHelper::thenGet2)
                .elseGet(testHelper::elseGet);

        assertThat(value, is(20));
    }

    @Test
    public void isTrue_thenGetElseIfThenGetElseGet_returnSecondThenGetValueFirstThenGetIsNotCalled_whenFirstExpressionIsFalse_andSecondIsTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        when(testHelper.thenGet2()).thenReturn(20);
        when(testHelper.elseGet()).thenReturn(30);
        If.isTrue(false)
                .thenGet(testHelper::thenGet1)
                .elseIf(true)
                .thenGet(testHelper::thenGet2)
                .elseGet(testHelper::elseGet);

        verify(testHelper, times(0)).thenGet1();
    }

    @Test
    public void isTrue_thenGetElseIfThenGetElseGet_returnFirstThenGetValueSecondElseGetIsNotCalled_whenFirstExpressionIsFalse_andSecondIsTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        when(testHelper.thenGet2()).thenReturn(20);
        when(testHelper.elseGet()).thenReturn(30);
        If.isTrue(false)
                .thenGet(testHelper::thenGet1)
                .elseIf(true)
                .thenGet(testHelper::thenGet2)
                .elseGet(testHelper::elseGet);

        verify(testHelper, times(0)).elseGet();
    }

    @Test
    public void isTrue_thenGetElseIfThenGetElseGet_returnElseGet_whenNoneOfTheExpressionAreTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        when(testHelper.thenGet2()).thenReturn(20);
        when(testHelper.elseGet()).thenReturn(30);
        Integer value = If.isTrue(false)
                .thenGet(testHelper::thenGet1)
                .elseIf(false)
                .thenGet(testHelper::thenGet2)
                .elseGet(testHelper::elseGet);

        assertThat(value, is(30));
    }

    @Test
    public void isTrue_thenGetElseIfThenGetElseGet_returnElseGetFirstThenIsNotCalled_whenNoneOfTheExpressionAreTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        when(testHelper.thenGet2()).thenReturn(20);
        when(testHelper.elseGet()).thenReturn(30);
        If.isTrue(false)
                .thenGet(testHelper::thenGet1)
                .elseIf(false)
                .thenGet(testHelper::thenGet2)
                .elseGet(testHelper::elseGet);

        verify(testHelper, times(0)).thenGet1();
    }

    @Test
    public void isTrue_thenGetElseIfThenGetElseGet_returnElseGetSecondThenIsNotCalled_whenNoneOfTheExpressionAreTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        when(testHelper.thenGet2()).thenReturn(20);
        when(testHelper.elseGet()).thenReturn(30);
        If.isTrue(false)
                .thenGet(testHelper::thenGet1)
                .elseIf(false)
                .thenGet(testHelper::thenGet2)
                .elseGet(testHelper::elseGet);

        verify(testHelper, times(0)).thenGet2();
    }

    @Test
    public void isTrue_thenGetElseIfThenGetElseValue_returnThenGet_whenExpressionIsTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        when(testHelper.thenGet2()).thenReturn(20);
        Integer value = If.isTrue(true)
                .thenGet(testHelper::thenGet1)
                .elseIf(false)
                .thenGet(testHelper::thenGet2)
                .elseValue(30);

        assertThat(value, is(10));
    }

    @Test
    public void isTrue_thenGetElseIfThenGetElseValue_secondThenGetIsNotCalled__whenExpressionIsTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        when(testHelper.thenGet2()).thenReturn(20);
        If.isTrue(true)
                .thenGet(testHelper::thenGet1)
                .elseIf(false)
                .thenGet(testHelper::thenGet2)
                .elseValue(30);

        verify(testHelper, times(0)).thenGet2();
    }

    @Test
    public void isTrue_thenGetElseIfThenGetElseValue_secondThenGetIsNotCalled_evenWhenElseIfIsTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        when(testHelper.thenGet2()).thenReturn(20);
        If.isTrue(true)
                .thenGet(testHelper::thenGet1)
                .elseIf(true)
                .thenGet(testHelper::thenGet2)
                .elseValue(30);

        verify(testHelper, times(0)).thenGet2();
    }

    @Test
    public void isTrue_thenGetElseIfThenGetElseValue_returnElseValue_whenExpressionsAreFalse() {
        when(testHelper.thenGet1()).thenReturn(10);
        when(testHelper.thenGet2()).thenReturn(20);
        Integer value = If.isTrue(false)
                .thenGet(testHelper::thenGet1)
                .elseIf(false)
                .thenGet(testHelper::thenGet2)
                .elseValue(30);

        assertThat(value, is(30));

    }

    @Test
    public void isTrue_thenGetElseIfThenGetElseThrow_returnFirstThenGet_whenExpressionsIsTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        when(testHelper.thenGet2()).thenReturn(20);
        Integer value = If.isTrue(true)
                .thenGet(testHelper::thenGet1)
                .elseIf(false)
                .thenGet(testHelper::thenGet2)
                .elseThrow(() -> new IllegalArgumentException("invalid argument"));

        assertThat(value, is(10));
    }

    @Test
    public void isTrue_thenGetElseIfThenGetElseThrow_secondThenGetIsNotCalled_whenExpressionsIsTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        when(testHelper.thenGet2()).thenReturn(20);
        If.isTrue(true)
                .thenGet(testHelper::thenGet1)
                .elseIf(false)
                .thenGet(testHelper::thenGet2)
                .elseThrow(() -> new IllegalArgumentException("invalid argument"));
        verify(testHelper, times(0)).thenGet2();
    }

    @Test
    public void isTrue_thenGetElseIfThenGetElseThrow_secondThenGetIsNotCalled_whenExpressionsIsTrue_evenWhenElseIfExpressionIsTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        when(testHelper.thenGet2()).thenReturn(20);
        If.isTrue(true)
                .thenGet(testHelper::thenGet1)
                .elseIf(true)
                .thenGet(testHelper::thenGet2)
                .elseThrow(() -> new IllegalArgumentException("invalid argument"));
        verify(testHelper, times(0)).thenGet2();
    }

    @Test
    public void isTrue_thenGetElseIfThenGetElseThrow_returnSecondThenGet_whenElseIfIsTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        when(testHelper.thenGet2()).thenReturn(20);
        final Integer value = If.isTrue(false)
                .thenGet(testHelper::thenGet1)
                .elseIf(true)
                .thenGet(testHelper::thenGet2)
                .elseThrow(() -> new IllegalArgumentException("invalid argument"));
        assertThat(value, is(20));
    }

    @Test
    public void isTrue_thenGetElseIfThenGetElseThrow_throwException_whenNoneOfTheExpressionAreTrue() {
        expectedException.expect(IllegalArgumentException.class);
        final Integer value = If.isTrue(false)
                .thenGet(testHelper::thenGet1)
                .elseIf(false)
                .thenGet(testHelper::thenGet2)
                .elseThrow(() -> new IllegalArgumentException("invalid argument"));
        assertThat(value, is(20));
    }

    @Test
    public void isTrue_thenGetElseIfThenGetElseThrow_firstThenGetIsNotCalled_whenNoneOfTheExpressionAreTrue() {
        try {
            If.isTrue(false)
                    .thenGet(testHelper::thenGet1)
                    .elseIf(false)
                    .thenGet(testHelper::thenGet2)
                    .elseThrow(() -> new IllegalArgumentException("invalid argument"));
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException exception) {
            verify(testHelper, times(0)).thenGet1();
        }
    }

    @Test
    public void isTrue_thenGetElseIfThenGetElseThrow_secondThenGetIsNotCalled_whenNoneOfTheExpressionAreTrue() {
        try {
            If.isTrue(false)
                    .thenGet(testHelper::thenGet1)
                    .elseIf(false)
                    .thenGet(testHelper::thenGet2)
                    .elseThrow(() -> new IllegalArgumentException("invalid argument"));
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException exception) {
            verify(testHelper, times(0)).thenGet2();
        }
    }

    @Test
    public void isTrue_thenGetElseIfThenValueElseGet_returnThenGet_whenExpressionIsTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        when(testHelper.elseGet()).thenReturn(30);
        final Integer value = If.isTrue(true)
                .thenGet(testHelper::thenGet1)
                .elseIf(false)
                .thenValue(20)
                .elseGet(testHelper::elseGet);
        assertThat(value, is(10));
    }

    @Test
    public void isTrue_thenGetElseIfThenValueElseGet_elseGetIsNotCalled_whenExpressionIsTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        when(testHelper.elseGet()).thenReturn(30);
        If.isTrue(true)
                .thenGet(testHelper::thenGet1)
                .elseIf(false)
                .thenValue(20)
                .elseGet(testHelper::elseGet);

        verify(testHelper, times(0)).elseGet();
    }

    @Test
    public void isTrue_thenGetElseIfThenValueElseGet_returnThenValue_whenExpressionIsFalse_andElseIfIsTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        when(testHelper.elseGet()).thenReturn(30);
        final Integer value = If.isTrue(false)
                .thenGet(testHelper::thenGet1)
                .elseIf(true)
                .thenValue(20)
                .elseGet(testHelper::elseGet);

        assertThat(value, is(20));
    }

    @Test
    public void isTrue_thenGetElseIfThenValueElseGet_thenGetIsNotCalled_whenNoneOfTheExpressionAreTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        when(testHelper.elseGet()).thenReturn(30);
        If.isTrue(false)
                .thenGet(testHelper::thenGet1)
                .elseIf(false)
                .thenValue(20)
                .elseGet(testHelper::elseGet);

        verify(testHelper, times(0)).thenGet1();
    }

    @Test
    public void isTrue_thenGetElseIfThenValueElseGet_thenGetIsNotCalled_whenExpressionIsFalse_andElseIfIsTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        when(testHelper.elseGet()).thenReturn(30);
        If.isTrue(false)
                .thenGet(testHelper::thenGet1)
                .elseIf(true)
                .thenValue(20)
                .elseGet(testHelper::elseGet);

        verify(testHelper, times(0)).thenGet1();
    }

    @Test
    public void isTrue_thenGetElseIfThenValueElseGet_elseGetIsNotCalled_whenExpressionIsFalse_andElseIfIsTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        when(testHelper.elseGet()).thenReturn(30);
        If.isTrue(false)
                .thenGet(testHelper::thenGet1)
                .elseIf(true)
                .thenValue(20)
                .elseGet(testHelper::elseGet);

        verify(testHelper, times(0)).elseGet();
    }

    @Test
    public void isTrue_thenGetElseIfThenValueElseGet_returnElseGet_whenNoneOfTheExpressionAreTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        when(testHelper.elseGet()).thenReturn(30);
        final Integer value = If.isTrue(false)
                .thenGet(testHelper::thenGet1)
                .elseIf(false)
                .thenValue(20)
                .elseGet(testHelper::elseGet);

        assertThat(value, is(30));
    }

    @Test
    public void isTrue_thenGetElseIfThenValueElseValue_returnThenGet_whenExpressionIsTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        final Integer value = If.isTrue(true)
                .thenGet(testHelper::thenGet1)
                .elseIf(false)
                .thenValue(20)
                .elseValue(30);

        assertThat(value, is(10));
    }

    @Test
    public void isTrue_thenGetElseIfThenValueElseValue_returnThenValue_whenExpressionIsFalse_andElseIfExpressionIsTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        final Integer value = If.isTrue(false)
                .thenGet(testHelper::thenGet1)
                .elseIf(true)
                .thenValue(20)
                .elseValue(30);

        assertThat(value, is(20));
    }

    @Test
    public void isTrue_thenGetElseIfThenValueElseValue_thenGetIsNotCalled_whenExpressionIsFalse_andElseIfExpressionIsTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        If.isTrue(false)
                .thenGet(testHelper::thenGet1)
                .elseIf(true)
                .thenValue(20)
                .elseValue(30);
        verify(testHelper, times(0)).thenGet1();
    }

    @Test
    public void isTrue_thenGetElseIfThenValueElseValue_returnElseValue_whenAllExpressionsAreFalse() {
        when(testHelper.thenGet1()).thenReturn(10);
        final Integer value = If.isTrue(false)
                .thenGet(testHelper::thenGet1)
                .elseIf(false)
                .thenValue(20)
                .elseValue(30);

        assertThat(value, is(30));
    }

    @Test
    public void isTrue_thenGetElseIfThenValueElseThrow_returnThenGet_whenExpressionIsTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        final Integer value = If.isTrue(true)
                .thenGet(testHelper::thenGet1)
                .elseIf(false)
                .thenValue(20)
                .elseThrow(() -> new IllegalArgumentException("some value"));

        assertThat(value, is(10));
    }

    @Test
    public void isTrue_thenGetElseIfThenValueElseThrow_returnThenGet_whenExpressionIsTrue_andElseIfIsAlsoTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        final Integer value = If.isTrue(true)
                .thenGet(testHelper::thenGet1)
                .elseIf(true)
                .thenValue(20)
                .elseThrow(() -> new IllegalArgumentException("some value"));

        assertThat(value, is(10));
    }

    @Test
    public void isTrue_thenGetElseIfThenValueElseThrow_returnThenValue_whenExpressionIsFalse_andElseIfIsTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        final Integer value = If.isTrue(false)
                .thenGet(testHelper::thenGet1)
                .elseIf(true)
                .thenValue(20)
                .elseThrow(() -> new IllegalArgumentException("some value"));

        assertThat(value, is(20));
    }

    @Test
    public void isTrue_thenGetElseIfThenValueElseThrow_thenGetIsNotCalled_whenExpressionIsFalse_andElseIfIsTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        If.isTrue(false)
                .thenGet(testHelper::thenGet1)
                .elseIf(true)
                .thenValue(20)
                .elseThrow(() -> new IllegalArgumentException("some value"));

        verify(testHelper, times(0)).thenGet1();
    }

    @Test
    public void isTrue_thenGetElseIfThenValueElseThrow_throwException_whenAllExpressionsAreFalse() {
        expectedException.expect(IllegalArgumentException.class);
        If.isTrue(false)
                .thenGet(testHelper::thenGet1)
                .elseIf(false)
                .thenValue(20)
                .elseThrow(() -> new IllegalArgumentException("some value"));
    }

    @Test
    public void isTrue_thenGetElseIfThenValueElseThrow_ThenGetIsNotCalled_whenAllExpressionsAreFalse() {
        try {
            If.isTrue(false)
                    .thenGet(testHelper::thenGet1)
                    .elseIf(false)
                    .thenValue(20)
                    .elseThrow(() -> new IllegalArgumentException("some value"));
        } catch (IllegalArgumentException exception) {
            verify(testHelper, times(0)).thenGet1();
        }
    }

    @Test
    public void isTrue_thenGetElseIfThenThrowElseGet_returnThenGet_whenExpressionIsTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        when(testHelper.elseGet()).thenReturn(30);
        final Integer value = If.isTrue(true)
                .thenGet(testHelper::thenGet1)
                .elseIf(false)
                .thenThrow(() -> new IllegalArgumentException("Exception"))
                .elseGet(testHelper::elseGet);
        assertThat(value, is(10));
    }

    @Test
    public void isTrue_thenGetElseIfThenThrowElseGet_elseGetIsNotCalled_whenExpressionIsTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        when(testHelper.elseGet()).thenReturn(30);
        If.isTrue(true)
                .thenGet(testHelper::thenGet1)
                .elseIf(false)
                .thenThrow(() -> new IllegalArgumentException("Exception"))
                .elseGet(testHelper::elseGet);

        verify(testHelper, times(0)).elseGet();
    }

    @Test
    public void isTrue_thenGetElseIfThenThrowElseGet_throwException_whenExpressionIsFalse_andElseIfIsTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        expectedException.expect(IllegalArgumentException.class);
        final Integer value = If.isTrue(false)
                .thenGet(testHelper::thenGet1)
                .elseIf(true)
                .thenThrow(() -> new IllegalArgumentException("Exception"))
                .elseGet(testHelper::elseGet);

        assertThat(value, is(20));
    }

    @Test
    public void isTrue_thenGetElseIfThenThrowElseGet_thenGetIsNotCalled_whenNoneOfTheExpressionAreTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        when(testHelper.elseGet()).thenReturn(30);
        If.isTrue(false)
                .thenGet(testHelper::thenGet1)
                .elseIf(false)
                .thenThrow(() -> new IllegalArgumentException("Exception"))
                .elseGet(testHelper::elseGet);

        verify(testHelper, times(0)).thenGet1();
    }

    @Test
    public void isTrue_thenGetElseIfThenThrowElseGet_thenGetIsNotCalled_whenExpressionIsFalse_andElseIfIsTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        when(testHelper.elseGet()).thenReturn(30);
        try {
            If.isTrue(false)
                    .thenGet(testHelper::thenGet1)
                    .elseIf(true)
                    .thenThrow(() -> new IllegalArgumentException("Exception"))
                    .elseGet(testHelper::elseGet);
        } catch (IllegalArgumentException exception) {
            verify(testHelper, times(0)).thenGet1();
        }
    }

    @Test
    public void isTrue_thenGetElseIfThenThrowElseGet_elseGetIsNotCalled_whenExpressionIsFalse_andElseIfIsTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        when(testHelper.elseGet()).thenReturn(30);
        try {
            If.isTrue(false)
                    .thenGet(testHelper::thenGet1)
                    .elseIf(true)
                    .thenThrow(() -> new IllegalArgumentException("Exception"))
                    .elseGet(testHelper::elseGet);
        } catch (IllegalArgumentException exception) {
            verify(testHelper, times(0)).elseGet();
        }
    }

    @Test
    public void isTrue_thenGetElseIfThenThrowElseGet_returnElseGet_whenNoneOfTheExpressionAreTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        when(testHelper.elseGet()).thenReturn(30);
        final Integer value = If.isTrue(false)
                .thenGet(testHelper::thenGet1)
                .elseIf(false)
                .thenThrow(() -> new IllegalArgumentException("Exception"))
                .elseGet(testHelper::elseGet);

        assertThat(value, is(30));
    }

    @Test
    public void isTrue_thenGetElseIfThenThrowElseValue_returnThenGet_whenExpressionIsTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        final Integer value = If.isTrue(true)
                .thenGet(testHelper::thenGet1)
                .elseIf(false)
                .thenThrow(() -> new IllegalArgumentException("Exception"))
                .elseValue(30);

        assertThat(value, is(10));
    }

    @Test
    public void isTrue_thenGetElseIfThenThrowElseValue_returnThenValue_whenExpressionIsFalse_andElseIfExpressionIsTrue() {
        expectedException.expect(IllegalArgumentException.class);
        If.isTrue(false)
                .thenGet(testHelper::thenGet1)
                .elseIf(true)
                .thenThrow(() -> new IllegalArgumentException("Exception"))
                .elseValue(30);
    }

    @Test
    public void isTrue_thenGetElseIfThenThrowElseValue_thenGetIsNotCalled_whenExpressionIsFalse_andElseIfExpressionIsTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        try {
            If.isTrue(false)
                    .thenGet(testHelper::thenGet1)
                    .elseIf(true)
                    .thenThrow(() -> new IllegalArgumentException("Exception"))
                    .elseValue(30);
        } catch (IllegalArgumentException exception) {
            verify(testHelper, times(0)).thenGet1();
        }
    }

    @Test
    public void isTrue_thenGetElseIfThenThrowElseValue_returnElseValue_whenAllExpressionsAreFalse() {
        when(testHelper.thenGet1()).thenReturn(10);
        final Integer value = If.isTrue(false)
                .thenGet(testHelper::thenGet1)
                .elseIf(false)
                .thenThrow(() -> new IllegalArgumentException("Exception"))
                .elseValue(30);

        assertThat(value, is(30));
    }

    @Test
    public void isTrue_thenGetElseIfThenThrowElseThrow_returnThenGet_whenExpressionIsTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        final Integer value = If.isTrue(true)
                .thenGet(testHelper::thenGet1)
                .elseIf(false)
                .thenThrow(() -> new IllegalArgumentException("Exception"))
                .elseThrow(() -> new IllegalArgumentException("some value"));

        assertThat(value, is(10));
    }

    @Test
    public void isTrue_thenGetElseIfThenThrowElseThrow_returnThenGet_whenExpressionIsTrue_andElseIfIsAlsoTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        final Integer value = If.isTrue(true)
                .thenGet(testHelper::thenGet1)
                .elseIf(true)
                .thenThrow(() -> new IllegalArgumentException("Exception"))
                .elseThrow(() -> new IllegalArgumentException("some value"));

        assertThat(value, is(10));
    }

    @Test
    public void isTrue_thenGetElseIfThenThrowElseThrow_throwException_whenExpressionIsFalse_andElseIfIsTrue() throws Exception {
        when(testHelper.thenGet1()).thenReturn(10);
        expectedException.expect(Exception.class);
        final Integer value = If.isTrue(false)
                .thenGet(testHelper::thenGet1)
                .elseIf(true)
                .thenThrow(() -> new Exception("Exception"))
                .elseThrow(() -> new IllegalArgumentException("some value"));

        assertThat(value, is(20));
    }

    @Test
    public void isTrue_thenGetElseIfThenThrowElseThrow_thenGetIsNotCalled_whenExpressionIsFalse_andElseIfIsTrue() {
        when(testHelper.thenGet1()).thenReturn(10);
        try {
            If.isTrue(false)
                    .thenGet(testHelper::thenGet1)
                    .elseIf(true)
                    .thenThrow(() -> new IllegalArgumentException("Exception"))
                    .elseThrow(() -> new IllegalArgumentException("some value"));
        } catch (IllegalArgumentException exception) {
            verify(testHelper, times(0)).thenGet1();
        }
    }

    @Test
    public void isTrue_thenGetElseIfThenThrowElseThrow_throwException_whenAllExpressionsAreFalse() {
        expectedException.expect(IllegalArgumentException.class);
        If.isTrue(false)
                .thenGet(testHelper::thenGet1)
                .elseIf(false)
                .thenThrow(() -> new IllegalArgumentException("Exception"))
                .elseThrow(() -> new IllegalArgumentException("some value"));
    }

    @Test
    public void isTrue_thenGetElseIfThenThrowElseThrow_ThenGetIsNotCalled_whenAllExpressionsAreFalse() {
        try {
            If.isTrue(false)
                    .thenGet(testHelper::thenGet1)
                    .elseIf(false)
                    .thenThrow(() -> new IllegalArgumentException("Exception"))
                    .elseThrow(() -> new IllegalArgumentException("some value"));
        } catch (IllegalArgumentException exception) {
            verify(testHelper, times(0)).thenGet1();
        }
    }

    @Test
    public void isTrue_thenValueElseIfThenGetElseGet_returnFirstThenGetValue() {
        when(testHelper.elseGet()).thenReturn(30);
        Integer value = If.isTrue(true)
                .thenValue(10)
                .elseIf(false)
                .thenGet(testHelper::thenGet2)
                .elseGet(testHelper::elseGet);

        assertThat(value, is(10));
    }

    @Test
    public void isTrue_thenValueElseIfThenGetElseGet_returnFirstThenGetValueSecondThenGetIsNotCalled() {
        when(testHelper.thenGet2()).thenReturn(20);
        when(testHelper.elseGet()).thenReturn(30);
        If.isTrue(true)
                .thenValue(10)
                .elseIf(false)
                .thenGet(testHelper::thenGet2)
                .elseGet(testHelper::elseGet);

        verify(testHelper, times(0)).thenGet2();
    }

    @Test
    public void isTrue_thenValueElseIfThenGetElseGet_returnFirstThenGetValueSecondElseGetIsNotCalled() {
        when(testHelper.thenGet2()).thenReturn(20);
        when(testHelper.elseGet()).thenReturn(30);
        If.isTrue(true)
                .thenValue(10)
                .elseIf(false)
                .thenGet(testHelper::thenGet2)
                .elseGet(testHelper::elseGet);

        verify(testHelper, times(0)).elseGet();
    }

    @Test
    public void isTrue_thenValueElseIfThenGetElseGet_returnFirstThenGetValueSecondElseGetIsNotCalled_whenSecondExpressionIsAlsoTrue() {
        when(testHelper.thenGet2()).thenReturn(20);
        when(testHelper.elseGet()).thenReturn(30);
        If.isTrue(true)
                .thenValue(10)
                .elseIf(true)
                .thenGet(testHelper::thenGet2)
                .elseGet(testHelper::elseGet);

        verify(testHelper, times(0)).thenGet2();
    }

    @Test
    public void isTrue_thenValueElseIfThenGetElseGet_returnSecondThenGetValue_whenFirstExpressionIsFalse_andSecondIsTrue() {
        when(testHelper.thenGet2()).thenReturn(20);
        when(testHelper.elseGet()).thenReturn(30);
        Integer value = If.isTrue(false)
                .thenValue(10)
                .elseIf(true)
                .thenGet(testHelper::thenGet2)
                .elseGet(testHelper::elseGet);

        assertThat(value, is(20));
    }

    @Test
    public void isTrue_thenValueElseIfThenGetElseGet_returnFirstThenGetValueSecondElseGetIsNotCalled_whenFirstExpressionIsFalse_andSecondIsTrue() {
        when(testHelper.thenGet2()).thenReturn(20);
        when(testHelper.elseGet()).thenReturn(30);
        If.isTrue(false)
                .thenValue(10)
                .elseIf(true)
                .thenGet(testHelper::thenGet2)
                .elseGet(testHelper::elseGet);

        verify(testHelper, times(0)).elseGet();
    }

    @Test
    public void isTrue_thenValueElseIfThenGetElseGet_returnElseGet_whenNoneOfTheExpressionAreTrue() {
        when(testHelper.thenGet2()).thenReturn(20);
        when(testHelper.elseGet()).thenReturn(30);
        Integer value = If.isTrue(false)
                .thenValue(10)
                .elseIf(false)
                .thenGet(testHelper::thenGet2)
                .elseGet(testHelper::elseGet);

        assertThat(value, is(30));
    }

    @Test
    public void isTrue_thenValueElseIfThenGetElseGet_returnElseGetSecondThenIsNotCalled_whenNoneOfTheExpressionAreTrue() {
        when(testHelper.thenGet2()).thenReturn(20);
        when(testHelper.elseGet()).thenReturn(30);
        If.isTrue(false)
                .thenValue(10)
                .elseIf(false)
                .thenGet(testHelper::thenGet2)
                .elseGet(testHelper::elseGet);

        verify(testHelper, times(0)).thenGet2();
    }

    @Test
    public void isTrue_thenValueElseIfThenGetElseValue_returnThenGet_whenExpressionIsTrue() {
        when(testHelper.thenGet2()).thenReturn(20);
        Integer value = If.isTrue(true)
                .thenValue(10)
                .elseIf(false)
                .thenGet(testHelper::thenGet2)
                .elseValue(30);

        assertThat(value, is(10));
    }

    @Test
    public void isTrue_thenValueElseIfThenGetElseValue_secondThenGetIsNotCalled__whenExpressionIsTrue() {
        when(testHelper.thenGet2()).thenReturn(20);
        If.isTrue(true)
                .thenValue(10)
                .elseIf(false)
                .thenGet(testHelper::thenGet2)
                .elseValue(30);

        verify(testHelper, times(0)).thenGet2();
    }

    @Test
    public void isTrue_thenValueElseIfThenGetElseValue_secondThenGetIsNotCalled_evenWhenElseIfIsTrue() {
        when(testHelper.thenGet2()).thenReturn(20);
        If.isTrue(true)
                .thenValue(10)
                .elseIf(true)
                .thenGet(testHelper::thenGet2)
                .elseValue(30);

        verify(testHelper, times(0)).thenGet2();
    }

    @Test
    public void isTrue_thenValueElseIfThenGetElseValue_returnElseValue_whenExpressionsAreFalse() {
        when(testHelper.thenGet2()).thenReturn(20);
        Integer value = If.isTrue(false)
                .thenValue(10)
                .elseIf(false)
                .thenGet(testHelper::thenGet2)
                .elseValue(30);

        assertThat(value, is(30));

    }

    @Test
    public void isTrue_thenValueElseIfThenGetElseThrow_returnFirstThenGet_whenExpressionsIsTrue() {
        when(testHelper.thenGet2()).thenReturn(20);
        Integer value = If.isTrue(true)
                .thenValue(10)
                .elseIf(false)
                .thenGet(testHelper::thenGet2)
                .elseThrow(() -> new IllegalArgumentException("invalid argument"));

        assertThat(value, is(10));
    }

    @Test
    public void isTrue_thenValueElseIfThenGetElseThrow_secondThenGetIsNotCalled_whenExpressionsIsTrue() {
        when(testHelper.thenGet2()).thenReturn(20);
        If.isTrue(true)
                .thenValue(10)
                .elseIf(false)
                .thenGet(testHelper::thenGet2)
                .elseThrow(() -> new IllegalArgumentException("invalid argument"));
        verify(testHelper, times(0)).thenGet2();
    }

    @Test
    public void isTrue_thenValueElseIfThenGetElseThrow_secondThenGetIsNotCalled_whenExpressionsIsTrue_evenWhenElseIfExpressionIsTrue() {
        when(testHelper.thenGet2()).thenReturn(20);
        If.isTrue(true)
                .thenValue(10)
                .elseIf(true)
                .thenGet(testHelper::thenGet2)
                .elseThrow(() -> new IllegalArgumentException("invalid argument"));
        verify(testHelper, times(0)).thenGet2();
    }

    @Test
    public void isTrue_thenValueElseIfThenGetElseThrow_returnSecondThenGet_whenElseIfIsTrue() {
        when(testHelper.thenGet2()).thenReturn(20);
        final Integer value = If.isTrue(false)
                .thenValue(10)
                .elseIf(true)
                .thenGet(testHelper::thenGet2)
                .elseThrow(() -> new IllegalArgumentException("invalid argument"));
        assertThat(value, is(20));
    }

    @Test
    public void isTrue_thenValueElseIfThenGetElseThrow_throwException_whenNoneOfTheExpressionAreTrue() {
        expectedException.expect(IllegalArgumentException.class);
        final Integer value = If.isTrue(false)
                .thenValue(10)
                .elseIf(false)
                .thenGet(testHelper::thenGet2)
                .elseThrow(() -> new IllegalArgumentException("invalid argument"));
        assertThat(value, is(20));
    }

    @Test
    public void isTrue_thenValueElseIfThenGetElseThrow_secondThenGetIsNotCalled_whenNoneOfTheExpressionAreTrue() {
        try {
            If.isTrue(false)
                    .thenValue(10)
                    .elseIf(false)
                    .thenGet(testHelper::thenGet2)
                    .elseThrow(() -> new IllegalArgumentException("invalid argument"));
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException exception) {
            verify(testHelper, times(0)).thenGet2();
        }
    }

    @Test
    public void isTrue_thenValueElseIfThenValueElseGet_returnThenGet_whenExpressionIsTrue() {
        when(testHelper.elseGet()).thenReturn(30);
        final Integer value = If.isTrue(true)
                .thenValue(10)
                .elseIf(false)
                .thenValue(20)
                .elseGet(testHelper::elseGet);
        assertThat(value, is(10));
    }

    @Test
    public void isTrue_thenValueElseIfThenValueElseGet_elseGetIsNotCalled_whenExpressionIsTrue() {
        when(testHelper.elseGet()).thenReturn(30);
        If.isTrue(true)
                .thenValue(10)
                .elseIf(false)
                .thenValue(20)
                .elseGet(testHelper::elseGet);

        verify(testHelper, times(0)).elseGet();
    }

    @Test
    public void isTrue_thenValueElseIfThenValueElseGet_returnThenValue_whenExpressionIsFalse_andElseIfIsTrue() {
        when(testHelper.elseGet()).thenReturn(30);
        final Integer value = If.isTrue(false)
                .thenValue(10)
                .elseIf(true)
                .thenValue(20)
                .elseGet(testHelper::elseGet);

        assertThat(value, is(20));
    }

    @Test
    public void isTrue_thenValueElseIfThenValueElseGet_elseGetIsNotCalled_whenExpressionIsFalse_andElseIfIsTrue() {
        when(testHelper.elseGet()).thenReturn(30);
        If.isTrue(false)
                .thenValue(10)
                .elseIf(true)
                .thenValue(20)
                .elseGet(testHelper::elseGet);

        verify(testHelper, times(0)).elseGet();
    }

    @Test
    public void isTrue_thenValueElseIfThenValueElseGet_returnElseGet_whenNoneOfTheExpressionAreTrue() {
        when(testHelper.elseGet()).thenReturn(30);
        final Integer value = If.isTrue(false)
                .thenValue(10)
                .elseIf(false)
                .thenValue(20)
                .elseGet(testHelper::elseGet);

        assertThat(value, is(30));
    }

    @Test
    public void isTrue_thenValueElseIfThenValueElseValue_returnThenGet_whenExpressionIsTrue() {
        final Integer value = If.isTrue(true)
                .thenValue(10)
                .elseIf(false)
                .thenValue(20)
                .elseValue(30);

        assertThat(value, is(10));
    }

    @Test
    public void isTrue_thenValueElseIfThenValueElseValue_returnThenValue_whenExpressionIsFalse_andElseIfExpressionIsTrue() {
        final Integer value = If.isTrue(false)
                .thenValue(10)
                .elseIf(true)
                .thenValue(20)
                .elseValue(30);

        assertThat(value, is(20));
    }

    @Test
    public void isTrue_thenValueElseIfThenValueElseValue_returnElseValue_whenAllExpressionsAreFalse() {
        final Integer value = If.isTrue(false)
                .thenValue(10)
                .elseIf(false)
                .thenValue(20)
                .elseValue(30);

        assertThat(value, is(30));
    }

    @Test
    public void isTrue_thenValueElseIfThenValueElseThrow_returnThenGet_whenExpressionIsTrue() {
        final Integer value = If.isTrue(true)
                .thenValue(10)
                .elseIf(false)
                .thenValue(20)
                .elseThrow(() -> new IllegalArgumentException("some value"));

        assertThat(value, is(10));
    }

    @Test
    public void isTrue_thenValueElseIfThenValueElseThrow_returnThenGet_whenExpressionIsTrue_andElseIfIsAlsoTrue() {
        final Integer value = If.isTrue(true)
                .thenValue(10)
                .elseIf(true)
                .thenValue(20)
                .elseThrow(() -> new IllegalArgumentException("some value"));

        assertThat(value, is(10));
    }

    @Test
    public void isTrue_thenValueElseIfThenValueElseThrow_returnThenValue_whenExpressionIsFalse_andElseIfIsTrue() {
        final Integer value = If.isTrue(false)
                .thenValue(10)
                .elseIf(true)
                .thenValue(20)
                .elseThrow(() -> new IllegalArgumentException("some value"));

        assertThat(value, is(20));
    }

    @Test
    public void isTrue_thenValueElseIfThenValueElseThrow_throwException_whenAllExpressionsAreFalse() {
        expectedException.expect(IllegalArgumentException.class);
        If.isTrue(false)
                .thenValue(10)
                .elseIf(false)
                .thenValue(20)
                .elseThrow(() -> new IllegalArgumentException("some value"));
    }

    @Test
    public void isTrue_thenValueElseIfThenThrowElseGet_returnThenGet_whenExpressionIsTrue() {
        when(testHelper.elseGet()).thenReturn(30);
        final Integer value = If.isTrue(true)
                .thenValue(10)
                .elseIf(false)
                .thenThrow(() -> new IllegalArgumentException("Exception"))
                .elseGet(testHelper::elseGet);
        assertThat(value, is(10));
    }

    @Test
    public void isTrue_thenValueElseIfThenThrowElseGet_elseGetIsNotCalled_whenExpressionIsTrue() {
        when(testHelper.elseGet()).thenReturn(30);
        If.isTrue(true)
                .thenValue(10)
                .elseIf(false)
                .thenThrow(() -> new IllegalArgumentException("Exception"))
                .elseGet(testHelper::elseGet);

        verify(testHelper, times(0)).elseGet();
    }

    @Test
    public void isTrue_thenValueElseIfThenThrowElseGet_throwException_whenExpressionIsFalse_andElseIfIsTrue() {
        expectedException.expect(IllegalArgumentException.class);
        final Integer value = If.isTrue(false)
                .thenValue(10)
                .elseIf(true)
                .thenThrow(() -> new IllegalArgumentException("Exception"))
                .elseGet(testHelper::elseGet);

        assertThat(value, is(20));
    }

    @Test
    public void isTrue_thenValueElseIfThenThrowElseGet_elseGetIsNotCalled_whenExpressionIsFalse_andElseIfIsTrue() {
        when(testHelper.elseGet()).thenReturn(30);
        try {
            If.isTrue(false)
                    .thenValue(10)
                    .elseIf(true)
                    .thenThrow(() -> new IllegalArgumentException("Exception"))
                    .elseGet(testHelper::elseGet);
        } catch (IllegalArgumentException exception) {
            verify(testHelper, times(0)).elseGet();
        }
    }

    @Test
    public void isTrue_thenValueElseIfThenThrowElseGet_returnElseGet_whenNoneOfTheExpressionAreTrue() {
        when(testHelper.elseGet()).thenReturn(30);
        final Integer value = If.isTrue(false)
                .thenValue(10)
                .elseIf(false)
                .thenThrow(() -> new IllegalArgumentException("Exception"))
                .elseGet(testHelper::elseGet);

        assertThat(value, is(30));
    }

    @Test
    public void isTrue_thenValueElseIfThenThrowElseValue_returnThenGet_whenExpressionIsTrue() {
        final Integer value = If.isTrue(true)
                .thenValue(10)
                .elseIf(false)
                .thenThrow(() -> new IllegalArgumentException("Exception"))
                .elseValue(30);

        assertThat(value, is(10));
    }

    @Test
    public void isTrue_thenValueElseIfThenThrowElseValue_returnThenValue_whenExpressionIsFalse_andElseIfExpressionIsTrue() {
        expectedException.expect(IllegalArgumentException.class);
        If.isTrue(false)
                .thenValue(10)
                .elseIf(true)
                .thenThrow(() -> new IllegalArgumentException("Exception"))
                .elseValue(30);
    }


    @Test
    public void isTrue_thenValueElseIfThenThrowElseValue_returnElseValue_whenAllExpressionsAreFalse() {
        final Integer value = If.isTrue(false)
                .thenValue(10)
                .elseIf(false)
                .thenThrow(() -> new IllegalArgumentException("Exception"))
                .elseValue(30);

        assertThat(value, is(30));
    }

    @Test
    public void isTrue_thenValueElseIfThenThrowElseThrow_returnThenGet_whenExpressionIsTrue() {
        final Integer value = If.isTrue(true)
                .thenValue(10)
                .elseIf(false)
                .thenThrow(() -> new IllegalArgumentException("Exception"))
                .elseThrow(() -> new IllegalArgumentException("some value"));

        assertThat(value, is(10));
    }

    @Test
    public void isTrue_thenValueElseIfThenThrowElseThrow_returnThenGet_whenExpressionIsTrue_andElseIfIsAlsoTrue() {
        final Integer value = If.isTrue(true)
                .thenValue(10)
                .elseIf(true)
                .thenThrow(() -> new IllegalArgumentException("Exception"))
                .elseThrow(() -> new IllegalArgumentException("some value"));

        assertThat(value, is(10));
    }

    @Test
    public void isTrue_thenValueElseIfThenThrowElseThrow_throwException_whenExpressionIsFalse_andElseIfIsTrue() throws Exception {
        expectedException.expect(Exception.class);
        final Integer value = If.isTrue(false)
                .thenValue(10)
                .elseIf(true)
                .thenThrow(() -> new Exception("Exception"))
                .elseThrow(() -> new IllegalArgumentException("some value"));

        assertThat(value, is(20));
    }

    @Test
    public void isTrue_thenValueElseIfThenThrowElseThrow_throwException_whenAllExpressionsAreFalse() {
        expectedException.expect(IllegalArgumentException.class);
        If.isTrue(false)
                .thenValue(10)
                .elseIf(false)
                .thenThrow(() -> new IllegalArgumentException("Exception"))
                .elseThrow(() -> new IllegalArgumentException("some value"));
    }

    @Test
    public void isTrue_thenThrowElseIfThenGetElseGet_throwException() {
        when(testHelper.elseGet()).thenReturn(30);
        expectedException.expect(IllegalArgumentException.class);
        Integer value = If.isTrue(true)
                .thenThrow(() -> new IllegalArgumentException("ohh exception"))
                .elseIf(false)
                .thenGet(testHelper::thenGet2)
                .elseGet(testHelper::elseGet);
    }

    @Test
    public void isTrue_thenThrowElseIfThenGetElseGet_throwException_andSecondThenGetIsNotCalled() {
        when(testHelper.thenGet2()).thenReturn(20);
        when(testHelper.elseGet()).thenReturn(30);
        try {
            If.isTrue(true)
                    .thenThrow(() -> new IllegalArgumentException("ohh exception"))
                    .elseIf(false)
                    .thenGet(testHelper::thenGet2)
                    .elseGet(testHelper::elseGet);
        } catch (IllegalArgumentException exception) {
            verify(testHelper, times(0)).thenGet2();
        }
    }

    @Test
    public void isTrue_thenThrowElseIfThenGetElseGet_throwException_andSecondElseGetIsNotCalled() {
        when(testHelper.thenGet2()).thenReturn(20);
        when(testHelper.elseGet()).thenReturn(30);
        try {
            If.isTrue(true)
                    .thenThrow(() -> new IllegalArgumentException("ohh exception"))
                    .elseIf(false)
                    .thenGet(testHelper::thenGet2)
                    .elseGet(testHelper::elseGet);
        } catch (IllegalArgumentException exception) {
            verify(testHelper, times(0)).elseGet();
        }
    }

    @Test
    public void isTrue_thenThrowElseIfThenGetElseGet_throwException_andSecondElseGetIsNotCalled_whenSecondExpressionIsAlsoTrue() {
        when(testHelper.thenGet2()).thenReturn(20);
        when(testHelper.elseGet()).thenReturn(30);
        try {
            If.isTrue(true)
                    .thenThrow(() -> new IllegalArgumentException("ohh exception"))
                    .elseIf(true)
                    .thenGet(testHelper::thenGet2)
                    .elseGet(testHelper::elseGet);
        } catch (IllegalArgumentException exception) {
            verify(testHelper, times(0)).thenGet2();
        }
    }

    @Test
    public void isTrue_thenThrowElseIfThenGetElseGet_returnSecondThenGetValue_whenFirstExpressionIsFalse_andSecondIsTrue() {
        when(testHelper.thenGet2()).thenReturn(20);
        when(testHelper.elseGet()).thenReturn(30);
        Integer value = If.isTrue(false)
                .thenThrow(() -> new IllegalArgumentException("ohh exception"))
                .elseIf(true)
                .thenGet(testHelper::thenGet2)
                .elseGet(testHelper::elseGet);

        assertThat(value, is(20));
    }

    @Test
    public void isTrue_thenThrowElseIfThenGetElseGet_elseGetIsNotCalled_whenFirstExpressionIsFalse_andSecondIsTrue() {
        when(testHelper.thenGet2()).thenReturn(20);
        when(testHelper.elseGet()).thenReturn(30);
        If.isTrue(false)
                .thenThrow(() -> new IllegalArgumentException("ohh exception"))
                .elseIf(true)
                .thenGet(testHelper::thenGet2)
                .elseGet(testHelper::elseGet);

        verify(testHelper, times(0)).elseGet();
    }

    @Test
    public void isTrue_thenThrowElseIfThenGetElseGet_returnElseGet_whenNoneOfTheExpressionAreTrue() {
        when(testHelper.thenGet2()).thenReturn(20);
        when(testHelper.elseGet()).thenReturn(30);
        Integer value = If.isTrue(false)
                .thenThrow(() -> new IllegalArgumentException("ohh exception"))
                .elseIf(false)
                .thenGet(testHelper::thenGet2)
                .elseGet(testHelper::elseGet);

        assertThat(value, is(30));
    }

    @Test
    public void isTrue_thenThrowElseIfThenGetElseGet_returnElseGetSecondThenIsNotCalled_whenNoneOfTheExpressionAreTrue() {
        when(testHelper.thenGet2()).thenReturn(20);
        when(testHelper.elseGet()).thenReturn(30);
        If.isTrue(false)
                .thenThrow(() -> new IllegalArgumentException("ohh exception"))
                .elseIf(false)
                .thenGet(testHelper::thenGet2)
                .elseGet(testHelper::elseGet);

        verify(testHelper, times(0)).thenGet2();
    }

    @Test
    public void isTrue_thenThrowElseIfThenGetElseValue_throwException_whenExpressionIsTrue() {
        when(testHelper.thenGet2()).thenReturn(20);
        expectedException.expect(IllegalArgumentException.class);
        Integer value = If.isTrue(true)
                .thenThrow(() -> new IllegalArgumentException("ohh exception"))
                .elseIf(false)
                .thenGet(testHelper::thenGet2)
                .elseValue(30);
    }

    @Test
    public void isTrue_thenThrowElseIfThenGetElseValue_secondThenGetIsNotCalled__whenExpressionIsTrue() {
        when(testHelper.thenGet2()).thenReturn(20);
        try {
            If.isTrue(true)
                    .thenThrow(() -> new IllegalArgumentException("ohh exception"))
                    .elseIf(false)
                    .thenGet(testHelper::thenGet2)
                    .elseValue(30);
        } catch (IllegalArgumentException exception) {
            verify(testHelper, times(0)).thenGet2();
        }
    }

    @Test
    public void isTrue_thenThrowElseIfThenGetElseValue_secondThenGetIsNotCalled_evenWhenElseIfIsTrue() {
        when(testHelper.thenGet2()).thenReturn(20);
        try {
            If.isTrue(true)
                    .thenThrow(() -> new IllegalArgumentException("ohh exception"))
                    .elseIf(true)
                    .thenGet(testHelper::thenGet2)
                    .elseValue(30);
        } catch (IllegalArgumentException exception) {
            verify(testHelper, times(0)).thenGet2();
        }
    }

    @Test
    public void isTrue_thenThrowElseIfThenGetElseValue_returnElseValue_whenExpressionsAreFalse() {
        when(testHelper.thenGet2()).thenReturn(20);
        Integer value = If.isTrue(false)
                .thenThrow(() -> new IllegalArgumentException("ohh exception"))
                .elseIf(false)
                .thenGet(testHelper::thenGet2)
                .elseValue(30);

        assertThat(value, is(30));

    }

    @Test
    public void isTrue_thenThrowElseIfThenGetElseThrow_throwException_whenExpressionsIsTrue() throws Exception {
        when(testHelper.thenGet2()).thenReturn(20);
        expectedException.expect(IllegalArgumentException.class);
        Integer value = If.isTrue(true)
                .thenThrow(() -> new IllegalArgumentException("ohh exception"))
                .elseIf(false)
                .thenGet(testHelper::thenGet2)
                .elseThrow(() -> new Exception("invalid argument"));
    }

    @Test
    public void isTrue_thenThrowElseIfThenGetElseThrow_secondThenGetIsNotCalled_whenExpressionsIsTrue() {
        when(testHelper.thenGet2()).thenReturn(20);
        try {
            If.isTrue(true)
                    .thenThrow(() -> new IllegalArgumentException("ohh exception"))
                    .elseIf(false)
                    .thenGet(testHelper::thenGet2)
                    .elseThrow(() -> new IllegalArgumentException("invalid argument"));
        } catch (IllegalArgumentException exception) {
            verify(testHelper, times(0)).thenGet2();
        }
    }

    @Test
    public void isTrue_thenThrowElseIfThenGetElseThrow_secondThenGetIsNotCalled_whenExpressionsIsTrue_evenWhenElseIfExpressionIsTrue() throws Exception {
        when(testHelper.thenGet2()).thenReturn(20);
        try {
            If.isTrue(true)
                    .thenThrow(() -> new IllegalArgumentException("ohh exception"))
                    .elseIf(true)
                    .thenGet(testHelper::thenGet2)
                    .elseThrow(() -> new Exception("invalid argument"));
        } catch (IllegalArgumentException exception) {
            verify(testHelper, times(0)).thenGet2();
        }
    }

    @Test
    public void isTrue_thenThrowElseIfThenGetElseThrow_returnSecondThenGet_whenElseIfIsTrue() {
        when(testHelper.thenGet2()).thenReturn(20);
        final Integer value = If.isTrue(false)
                .thenThrow(() -> new IllegalArgumentException("ohh exception"))
                .elseIf(true)
                .thenGet(testHelper::thenGet2)
                .elseThrow(() -> new IllegalArgumentException("invalid argument"));
        assertThat(value, is(20));
    }

    @Test
    public void isTrue_thenThrowElseIfThenGetElseThrow_throwException_whenNoneOfTheExpressionAreTrue() {
        expectedException.expect(IllegalArgumentException.class);
        final Integer value = If.isTrue(false)
                .thenThrow(() -> new IllegalArgumentException("ohh exception"))
                .elseIf(false)
                .thenGet(testHelper::thenGet2)
                .elseThrow(() -> new IllegalArgumentException("invalid argument"));
    }

    @Test
    public void isTrue_thenThrowElseIfThenGetElseThrow_secondThenGetIsNotCalled_whenNoneOfTheExpressionAreTrue() {
        try {
            If.isTrue(false)
                    .thenThrow(() -> new IllegalArgumentException("ohh exception"))
                    .elseIf(false)
                    .thenGet(testHelper::thenGet2)
                    .elseThrow(() -> new IllegalArgumentException("invalid argument"));
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException exception) {
            verify(testHelper, times(0)).thenGet2();
        }
    }

    @Test
    public void isTrue_thenThrowElseIfThenValueElseGet_throwException_whenExpressionIsTrue() {
        when(testHelper.elseGet()).thenReturn(30);
        expectedException.expect(IllegalArgumentException.class);
        If.isTrue(true)
                .thenThrow(() -> new IllegalArgumentException("ohh exception"))
                .elseIf(false)
                .thenValue(20)
                .elseGet(testHelper::elseGet);
    }

    @Test
    public void isTrue_thenThrowElseIfThenValueElseGet_elseGetIsNotCalled_whenExpressionIsTrue() {
        when(testHelper.elseGet()).thenReturn(30);
        try {
            If.isTrue(true)
                    .thenThrow(() -> new IllegalArgumentException("ohh exception"))
                    .elseIf(false)
                    .thenValue(20)
                    .elseGet(testHelper::elseGet);
        } catch (IllegalArgumentException exception) {
            verify(testHelper, times(0)).elseGet();
        }
    }

    @Test
    public void isTrue_thenThrowElseIfThenValueElseGet_returnThenValue_whenExpressionIsFalse_andElseIfIsTrue() {
        when(testHelper.elseGet()).thenReturn(30);
        final Integer value = If.isTrue(false)
                .thenThrow(() -> new IllegalArgumentException("ohh exception"))
                .elseIf(true)
                .thenValue(20)
                .elseGet(testHelper::elseGet);

        assertThat(value, is(20));
    }

    @Test
    public void isTrue_thenThrowElseIfThenValueElseGet_elseGetIsNotCalled_whenExpressionIsFalse_andElseIfIsTrue() {
        when(testHelper.elseGet()).thenReturn(30);
        If.isTrue(false)
                .thenThrow(() -> new IllegalArgumentException("ohh exception"))
                .elseIf(true)
                .thenValue(20)
                .elseGet(testHelper::elseGet);

        verify(testHelper, times(0)).elseGet();
    }

    @Test
    public void isTrue_thenThrowElseIfThenValueElseGet_returnElseGet_whenNoneOfTheExpressionAreTrue() {
        when(testHelper.elseGet()).thenReturn(30);
        final Integer value = If.isTrue(false)
                .thenThrow(() -> new IllegalArgumentException("ohh exception"))
                .elseIf(false)
                .thenValue(20)
                .elseGet(testHelper::elseGet);

        assertThat(value, is(30));
    }

    @Test
    public void isTrue_thenThrowElseIfThenValueElseValue_throwException_whenExpressionIsTrue() {
        expectedException.expect(IllegalArgumentException.class);
        final Integer value = If.isTrue(true)
                .thenThrow(() -> new IllegalArgumentException("ohh exception"))
                .elseIf(false)
                .thenValue(20)
                .elseValue(30);
    }

    @Test
    public void isTrue_thenThrowElseIfThenValueElseValue_throwException_whenExpressionIsFalse_andElseIfExpressionIsTrue() {
        final Integer value = If.isTrue(false)
                .thenThrow(() -> new IllegalArgumentException("ohh exception"))
                .elseIf(true)
                .thenValue(20)
                .elseValue(30);

        assertThat(value, is(20));
    }

    @Test
    public void isTrue_thenThrowElseIfThenValueElseValue_returnElseValue_whenAllExpressionsAreFalse() {
        final Integer value = If.isTrue(false)
                .thenThrow(() -> new IllegalArgumentException("ohh exception"))
                .elseIf(false)
                .thenValue(20)
                .elseValue(30);

        assertThat(value, is(30));
    }

    @Test
    public void isTrue_thenThrowElseIfThenValueElseThrow_throwException_whenExpressionIsTrue() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        final Integer value = If.isTrue(true)
                .thenThrow(() -> new IllegalArgumentException("ohh exception"))
                .elseIf(false)
                .thenValue(20)
                .elseThrow(() -> new Exception("some value"));
    }

    @Test
    public void isTrue_thenThrowElseIfThenValueElseThrow_throwException_whenExpressionIsTrue_andElseIfIsAlsoTrue() {
        expectedException.expect(IllegalArgumentException.class);
        If.isTrue(true)
                .thenThrow(() -> new IllegalArgumentException("ohh exception"))
                .elseIf(true)
                .thenValue(20)
                .elseThrow(() -> new IllegalArgumentException("some value"));
    }

    @Test
    public void isTrue_thenThrowElseIfThenValueElseThrow_returnThenValue_whenExpressionIsFalse_andElseIfIsTrue() {
        final Integer value = If.isTrue(false)
                .thenThrow(() -> new IllegalArgumentException("ohh exception"))
                .elseIf(true)
                .thenValue(20)
                .elseThrow(() -> new IllegalArgumentException("some value"));

        assertThat(value, is(20));
    }

    @Test
    public void isTrue_thenThrowElseIfThenValueElseThrow_throwException_whenAllExpressionsAreFalse() {
        expectedException.expect(IllegalArgumentException.class);
        If.isTrue(false)
                .thenThrow(() -> new IllegalArgumentException("ohh exception"))
                .elseIf(false)
                .thenValue(20)
                .elseThrow(() -> new IllegalArgumentException("some value"));
    }

    @Test
    public void isTrue_thenThrowElseIfThenThrowElseGet_throwException_whenExpressionIsTrue() throws Exception {
        when(testHelper.elseGet()).thenReturn(30);
        expectedException.expect(IllegalArgumentException.class);
        final Integer value = If.isTrue(true)
                .thenThrow(() -> new IllegalArgumentException("ohh exception"))
                .elseIf(false)
                .thenThrow(() -> new Exception("Exception"))
                .elseGet(testHelper::elseGet);
    }

    @Test
    public void isTrue_thenThrowElseIfThenThrowElseGet_elseGetIsNotCalled_whenExpressionIsTrue() {
        when(testHelper.elseGet()).thenReturn(30);
        try {
            If.isTrue(true)
                    .thenThrow(() -> new IllegalArgumentException("ohh exception"))
                    .elseIf(false)
                    .thenThrow(() -> new IllegalArgumentException("Exception"))
                    .elseGet(testHelper::elseGet);
        } catch (IllegalArgumentException exception) {
            verify(testHelper, times(0)).elseGet();
        }
    }

    @Test
    public void isTrue_thenThrowElseIfThenThrowElseGet_throwException_whenExpressionIsFalse_andElseIfIsTrue() {
        expectedException.expect(IllegalArgumentException.class);
        final Integer value = If.isTrue(false)
                .thenThrow(() -> new IllegalArgumentException("ohh exception"))
                .elseIf(true)
                .thenThrow(() -> new IllegalArgumentException("Exception"))
                .elseGet(testHelper::elseGet);

        assertThat(value, is(20));
    }

    @Test
    public void isTrue_thenThrowElseIfThenThrowElseGet_elseGetIsNotCalled_whenExpressionIsFalse_andElseIfIsTrue() {
        when(testHelper.elseGet()).thenReturn(30);
        try {
            If.isTrue(false)
                    .thenThrow(() -> new IllegalArgumentException("ohh exception"))
                    .elseIf(true)
                    .thenThrow(() -> new IllegalArgumentException("Exception"))
                    .elseGet(testHelper::elseGet);
        } catch (IllegalArgumentException exception) {
            verify(testHelper, times(0)).elseGet();
        }
    }

    @Test
    public void isTrue_thenThrowElseIfThenThrowElseGet_returnElseGet_whenNoneOfTheExpressionAreTrue() {
        when(testHelper.elseGet()).thenReturn(30);
        final Integer value = If.isTrue(false)
                .thenThrow(() -> new IllegalArgumentException("ohh exception"))
                .elseIf(false)
                .thenThrow(() -> new IllegalArgumentException("Exception"))
                .elseGet(testHelper::elseGet);

        assertThat(value, is(30));
    }

    @Test
    public void isTrue_thenThrowElseIfThenThrowElseValue_throwException_whenExpressionIsTrue() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        If.isTrue(true)
                .thenThrow(() -> new IllegalArgumentException("ohh exception"))
                .elseIf(false)
                .thenThrow(() -> new Exception("Exception"))
                .elseValue(30);
    }

    @Test
    public void isTrue_thenThrowElseIfThenThrowElseValue_returnThenValue_whenExpressionIsFalse_andElseIfExpressionIsTrue() {
        expectedException.expect(IllegalArgumentException.class);
        If.isTrue(false)
                .thenThrow(() -> new IllegalArgumentException("ohh exception"))
                .elseIf(true)
                .thenThrow(() -> new IllegalArgumentException("Exception"))
                .elseValue(30);
    }


    @Test
    public void isTrue_thenThrowElseIfThenThrowElseValue_returnElseValue_whenAllExpressionsAreFalse() {
        final Integer value = If.isTrue(false)
                .thenThrow(() -> new IllegalArgumentException("ohh exception"))
                .elseIf(false)
                .thenThrow(() -> new IllegalArgumentException("Exception"))
                .elseValue(30);

        assertThat(value, is(30));
    }

    @Test
    public void isTrue_thenThrowElseIfThenThrowElseThrow_throwException_whenExpressionIsTrue() {
        expectedException.expect(IllegalMonitorStateException.class);
        If.isTrue(true)
                .thenThrow(() -> new IllegalMonitorStateException("ohh exception"))
                .elseIf(false)
                .thenThrow(() -> new IllegalArgumentException("Exception"))
                .elseThrow(() -> new IllegalArgumentException("some value"));
    }

    @Test
    public void isTrue_thenThrowElseIfThenThrowElseThrow_throwException_whenExpressionIsTrue_andElseIfIsAlsoTrue() {
        expectedException.expect(IllegalMonitorStateException.class);
        If.isTrue(true)
                .thenThrow(() -> new IllegalMonitorStateException("ohh exception"))
                .elseIf(true)
                .thenThrow(() -> new IllegalArgumentException("Exception"))
                .elseThrow(() -> new IllegalArgumentException("some value"));
    }

    @Test
    public void isTrue_thenThrowElseIfThenThrowElseThrow_throwException_whenExpressionIsFalse_andElseIfIsTrue() throws Exception {
        expectedException.expect(Exception.class);
        If.isTrue(false)
                .thenThrow(() -> new IllegalArgumentException("ohh exception"))
                .elseIf(true)
                .thenThrow(() -> new Exception("Exception"))
                .elseThrow(() -> new IllegalArgumentException("some value"));
    }

    @Test
    public void isTrue_thenThrowElseIfThenThrowElseThrow_throwException_whenAllExpressionsAreFalse() {
        expectedException.expect(IllegalArgumentException.class);
        If.isTrue(false)
                .thenThrow(() -> new IllegalArgumentException("ohh exception"))
                .elseIf(false)
                .thenThrow(() -> new IllegalArgumentException("Exception"))
                .elseThrow(() -> new IllegalArgumentException("some value"));
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
    public void isFalseThen_thenThrow_throwThenThrowException_whenExpressionIsTrue() {
        expectedException.expect(IllegalArgumentException.class);
        If.isFalseThen(false).thenThrow(IllegalArgumentException::new);
    }

    @Test
    public void isFalseThen_thenThrow__doNotThrowException_whenExpressionIsFalse() {
        If.isFalseThen(true).thenThrow(IllegalArgumentException::new);
    }

}

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
    public void ifIsNull_thenValueElseValue_returnThenValue_whenObjectIsNull() {
        final String value = If
                .isNull(null)
                .thenValue("null")
                .elseValue("non null");

        assertThat(value, is("null"));
    }

    @Test
    public void ifIsNull_thenValueElseValue_returnElseValue_whenObjectIsNotNull() {
        final String value = If
                .isNull("somevalue")
                .thenValue("null")
                .elseValue("non null");

        assertThat(value, is("non null"));
    }

    @Test
    public void ifIsNull_thenGetElseValue_returnThenGetValue_whenObjectIsNull() {
        final String value = If
                .isNull(null)
                .thenGet(() -> "null")
                .elseValue("non null");

        assertThat(value, is("null"));
    }

    @Test
    public void ifIsNull_thenGetElseValue_returnElseValue_whenObjectIsNotNull() {
        final String value = If
                .isNull("somevalue")
                .thenGet(() -> "null")
                .elseValue("non null");

        assertThat(value, is("non null"));
    }

    @Test
    public void ifIsNull_thenValueElseMap_returnElseMapValue_whenObjectIsNotNull() {
        final String value = If
                .isNull("somevalue")
                .thenValue("null")
                .elseMap((some) -> some + " non null");

        assertThat(value, is("somevalue non null"));
    }

    @Test
    public void ifIsNull_thenValueElseMap_returnThenValue_whenObjectIsNull() {
        final String value = If
                .isNull(null)
                .thenValue("null")
                .elseMap((some) -> some + " non null");

        assertThat(value, is("null"));
    }

    @Test
    public void ifIsNull_thenGetElseMap_returnThenGetValue_whenObjectIsNull() {
        final String value = If
                .isNull(null)
                .thenGet(() -> "null")
                .elseMap((some) -> some + " non null");

        assertThat(value, is("null"));
    }

    @Test
    public void ifIsNull_thenGetElseMap_returnElseMapValue_whenObjectIsNotNull() {
        final String value = If
                .isNull("somevalue")
                .thenGet(() -> "null")
                .elseMap((some) -> some + " non null");

        assertThat(value, is("somevalue non null"));
    }

    @Test
    public void ifIsNull_thenThrowElseValue_throwThenThrowException_whenObjectIsNull() {
        expectedException.expect(RuntimeException.class);
        If.isNull(null)
                .thenThrow(() -> new RuntimeException("somevalue"))
                .elseValue("non null");
    }

    @Test
    public void ifIsNull_thenThrowElseValue_returnElseValue_whenObjectIsNotNull() {
        final String value = If
                .isNull("somevalue")
                .thenThrow(() -> new RuntimeException("somevalue"))
                .elseValue("non null");

        assertThat(value, is("non null"));
    }

    @Test
    public void ifIsNull_thenThrowElseMap_throwThenThrowException_whenObjectIsNull() {
        expectedException.expect(RuntimeException.class);
        If.isNull(null)
                .thenThrow(() -> new RuntimeException("somevalue"))
                .elseMap((v) -> "non null");
    }

    @Test
    public void ifIsNull_thenThrowElseMap_returnElseMapValue_whenObjectIsNotNull() {
        final String value = If
                .isNull("somevalue")
                .thenThrow(() -> new RuntimeException("somevalue"))
                .elseMap((v) -> v + " non null");

        assertThat(value, is("somevalue non null"));
    }

    @Test
    public void ifIsNull_thenValueElseThrow_returnThenValue_whenObjectIsNull() {
        final String value = If
                .isNull(null)
                .thenValue("null")
                .elseThrow(RuntimeException::new);

        assertThat(value, is("null"));
    }

    @Test
    public void ifIsNull_thenValueElseThrow_throwElseThrowException_whenObjectIsNotNull() {
        expectedException.expect(RuntimeException.class);
        If.isNull("not null")
                .thenValue("somevalue")
                .elseThrow(RuntimeException::new);
    }

    @Test
    public void ifIsNull_thenGetElseThrow_returnThenGetValue_whenObjectIsNull() {
        final String value = If
                .isNull(null)
                .thenGet(() -> "null")
                .elseThrow(RuntimeException::new);

        assertThat(value, is("null"));
    }


    @Test
    public void ifIsNull_thenGetElseThrow_throwElseThrowException_whenObjectIsNotNull() {
        expectedException.expect(RuntimeException.class);
        If.isNull("not null")
                .thenGet(() -> "somevalue")
                .elseThrow(RuntimeException::new);
    }

    @Test
    public void ifIsNull_thenThrowElseThrow_throwThenThrowException_whenObjectIsNull() {
        expectedException.expect(IllegalArgumentException.class);
        If.isNull(null)
                .thenThrow(IllegalArgumentException::new)
                .elseThrow(RuntimeException::new);
    }

    @Test
    public void ifIsNull_thenThrowElseThrow_throwElseThrowException_whenObjectIsNotNull() {
        expectedException.expect(RuntimeException.class);
        If.isNull("some")
                .thenThrow(IllegalArgumentException::new)
                .elseThrow(RuntimeException::new);
    }

    @Test
    public void isTrue_thenGetElseGet_returnThenGetValue_whenExpressionIsTrue() {
        Integer value = If.isTrue(true)
                .thenGet(() -> 10)
                .elseGet(() -> 20);

        assertThat(value, is(10));
    }

    @Test
    public void isTrue_thenGetElseGet_returnElseGetValue_whenExpressionIsFalse() {
        Integer value = If.isTrue(false)
                .thenGet(() -> 10)
                .elseGet(() -> 20);

        assertThat(value, is(20));
    }

    @Test
    public void isTrue_thenCallElseCall_callThenCall_whenExpressionIsTrue() {
        TestHelper testHelper = mock(TestHelper.class);
        If.isTrue(true)
                .thenCall(testHelper::thenCallMe)
                .elseCall(testHelper::elseCallMe);
        verify(testHelper).thenCallMe();
    }

    @Test
    public void isTrue_thenCallElseCall_callElseCall_whenExpressionIsFalse() {
        TestHelper testHelper = mock(TestHelper.class);
        If.isTrue(false)
                .thenCall(testHelper::thenCallMe)
                .elseCall(testHelper::elseCallMe);
        verify(testHelper).elseCallMe();
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
    public void isTrue_thenCallElseThrow_throwElseThrowException_whenExpressionIsFalse() {
        TestHelper testHelper = mock(TestHelper.class);

        expectedException.expect(IllegalArgumentException.class);
        If.isTrue(false)
                .thenCall(testHelper::thenCallMe)
                .elseThrow(IllegalArgumentException::new);
    }

    @Test
    public void isTrue_thenCallElseThrow_callThenCall_whenExpressionIsTrue() {
        TestHelper testHelper = mock(TestHelper.class);

        If.isTrue(true)
                .thenCall(testHelper::thenCallMe)
                .elseThrow(IllegalArgumentException::new);

        verify(testHelper).thenCallMe();
    }

    @Test
    public void isTrue_thenGetElseThrow_returnThenGet_whenExpressionIsTrue() {
        final Integer value = If.isTrue(true)
                .thenGet(() -> 34)
                .elseThrow(IllegalArgumentException::new);
        assertThat(value, is(34));
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
    public void isFalse_thenCallElseCall_callThenCall_whenExpressionIsFalse() {
        TestHelper testHelper = mock(TestHelper.class);
        If.isFalse(false)
                .thenCall(testHelper::thenCallMe)
                .elseCall(testHelper::elseCallMe);
        verify(testHelper).thenCallMe();
    }

    @Test
    public void isFalse_thenCallElseCall_callElseCall_whenExpressionIsTrue() {
        TestHelper testHelper = mock(TestHelper.class);
        If.isFalse(true)
                .thenCall(testHelper::thenCallMe)
                .elseCall(testHelper::elseCallMe);
        verify(testHelper).elseCallMe();
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
    public void isFalse_thenCallElseThrow_throwElseException_whenExpressionIsTrue() {
        TestHelper testHelper = mock(TestHelper.class);

        expectedException.expect(IllegalArgumentException.class);
        If.isFalse(true)
                .thenCall(testHelper::thenCallMe)
                .elseThrow(IllegalArgumentException::new);
    }

    @Test
    public void isFalse_thenCallElseThrow_callThenCall_whenExpressionIsFalse() {
        TestHelper testHelper = mock(TestHelper.class);

        If.isFalse(false)
                .thenCall(testHelper::thenCallMe)
                .elseThrow(IllegalArgumentException::new);

        verify(testHelper).thenCallMe();
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
        TestHelper testHelper = mock(TestHelper.class);

        If.isTrueThen(true).thenCall(testHelper::thenCallMe);

        verify(testHelper).thenCallMe();
    }

    @Test
    public void isTrueThen_thenCall_doNotCall_whenExpressionIsFalse() {
        TestHelper testHelper = mock(TestHelper.class);

        If.isTrueThen(false).thenCall(testHelper::thenCallMe);

        verify(testHelper, times(0)).thenCallMe();
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

    @Test
    public void isFalseThen_thenCall_callThenCall_whenExpressionIsTrue() {
        TestHelper testHelper = mock(TestHelper.class);

        If.isFalseThen(false).thenCall(testHelper::thenCallMe);

        verify(testHelper).thenCallMe();
    }

    @Test
    public void isFalseThen_thenCall_doNotCall_whenExpressionIsFalse() {
        TestHelper testHelper = mock(TestHelper.class);

        If.isFalseThen(true).thenCall(testHelper::thenCallMe);

        verify(testHelper, times(0)).thenCallMe();
    }

    @Test
    public void ifOrElse_thenGetElseGet_returnThenGetValue_whenSupplierEvaluatesToTrue() {

        final Integer value = If.orElse(() -> true, () -> 10, () -> 20);

        assertThat(value, is(10));
    }

    @Test
    public void nullOrElse_thenGetElseGet_returnThenReturn_whenIsNull() {

        final Integer value = If.nullOrElse(null, () -> 10, () -> 20);

        assertThat(value, is(10));
    }

    @Test
    public void nullOrElse_thenGetElseGet_returnElseGet_whenNonNull() {

        final Integer value = If.nullOrElse("202", () -> 10, () -> 20);

        assertThat(value, is(20));
    }

    @Test
    public void ifOrElse_thenGetElseGet_returnElseGetValue_whenSupplierEvaluatesToFalse() {

        final Integer value = If.orElse(() -> false, () -> 10, () -> 20);

        assertThat(value, is(20));
    }

    @Test
    public void ifOrElse_thenCallElseCall_callThenCall_whenSupplierEvaluatesToTrue() {
        TestHelper testHelper = mock(TestHelper.class);

        If.orElse(() -> true, testHelper::thenCallMe, testHelper::elseCallMe);

        verify(testHelper).thenCallMe();
    }

    @Test
    public void ifOrElse_thenCallElseCall_callElseCall_whenSupplierEvaluatesToFalse() {
        TestHelper testHelper = mock(TestHelper.class);

        If.orElse(() -> false, testHelper::thenCallMe, testHelper::elseCallMe);

        verify(testHelper).elseCallMe();
    }

    @Test
    public void isTrueThen_thenCall_callThenCall_whenSupplierEvaluatesToTrue() {
        TestHelper testHelper = mock(TestHelper.class);

        If.isTrueThen(() -> true, testHelper::thenCallMe);

        verify(testHelper).thenCallMe();
    }

    @Test
    public void isNullThen_thenCall_callThenCall_whenObjectIsNull() {
        TestHelper testHelper = mock(TestHelper.class);

        If.isNullThen(null, testHelper::thenCallMe);

        verify(testHelper).thenCallMe();
    }

    @Test
    public void isNullThen_thenCall_doNotCall_whenObjectIsNonNull() {
        TestHelper testHelper = mock(TestHelper.class);

        If.isNullThen("something", testHelper::thenCallMe);

        verify(testHelper, times(0)).thenCallMe();
    }

    @Test
    public void nullOrElse_thenGetElseGet_returnElseGetValue_whenObjectIsNonNull() {
        Integer value = If.nullOrElse("1000", () -> 100, Integer::valueOf);

        assertThat(value, is(1000));
    }

    @Test
    public void nullOrElse_thenGetElseGet_returnThenGet_whenObjectIsNull() {
        String conditionValue = null;
        Integer value = If.nullOrElse(conditionValue, () -> 100, Integer::valueOf);

        assertThat(value, is(100));
    }

    @Test
    public void isTrueThen_thenCall_callThenCall_whenSupplierEvaluatesToFalse() {
        TestHelper testHelper = mock(TestHelper.class);

        If.isTrueThen(() -> false, testHelper::thenCallMe);

        verify(testHelper, times(0)).thenCallMe();
    }

    @Test
    public void ifOrElse_thenGetElseGet_returnthenGetValue_whenExpressionEvaluatesToTrue() {

        final Integer value = If.orElse(true, () -> 10, () -> 20);

        assertThat(value, is(10));
    }

    @Test
    public void ifOrElse_thenGetElseGet_returnElseGetValue_whenExpressionEvaluatesToFalse() {

        final Integer value = If.orElse(false, () -> 10, () -> 20);

        assertThat(value, is(20));
    }

    @Test
    public void ifOrElse_thenValueElseValue_returnThenValue_whenExpressionEvaluatesToTrue() {

        final Integer value = If.orElse(true, 10, 20);

        assertThat(value, is(10));
    }

    @Test
    public void ifOrElse_thenValueElseValue_returnElseValue_returnElseReturnValue_whenExpressionEvaluatesToFalse() {

        final Integer value = If.orElse(false, 10, 20);

        assertThat(value, is(20));
    }

    @Test
    public void ifOrElse_thenValueElseValue_returnThenValue__whenSupplierIsTrue() {

        final Integer value = If.orElse(() -> true, 10, 20);

        assertThat(value, is(10));
    }

    @Test
    public void ifOrElse_thenValueElseValue_returnElseValue_whenSupplierIsFalse() {

        final Integer value = If.orElse(() -> false, 10, 20);

        assertThat(value, is(20));
    }

    @Test
    public void ifOrElse_thenCallElseCall_callThenCall_whenExpressionEvaluatesToTrue() {
        TestHelper testHelper = mock(TestHelper.class);

        If.orElse(true, testHelper::thenCallMe, testHelper::elseCallMe);

        verify(testHelper).thenCallMe();
    }

    @Test
    public void ifOrElse_thenCallElseCall_callElseCall_whenExpressionEvaluatesToFalse() {
        TestHelper testHelper = mock(TestHelper.class);

        If.orElse(false, testHelper::thenCallMe, testHelper::elseCallMe);

        verify(testHelper).elseCallMe();
    }

    @Test
    public void isTrueThen_callThenCall_whenExpressionEvaluatesToTrue() {
        TestHelper testHelper = mock(TestHelper.class);

        If.isTrueThen(true, testHelper::thenCallMe);

        verify(testHelper).thenCallMe();
    }

    @Test
    public void isTrueThen_thenCall_doNotCallSupplier_whenExpressionEvaluatesToFalse() {
        TestHelper testHelper = mock(TestHelper.class);

        If.isTrueThen(false, testHelper::thenCallMe);

        verify(testHelper, times(0)).thenCallMe();
    }

    static class TestHelper {
        void thenCallMe() {
            System.out.println("then call me");
        }

        void elseCallMe() {
            System.out.println("else call me");
        }
    }
}

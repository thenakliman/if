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
    public void ifIsNull_returnNullValue_whenObjectIsNull() {
        final String value = If
                .isNull(null)
                .thenReturn("null")
                .elseReturn("non null");

        assertThat(value, is("null"));
    }

    @Test
    public void ifIsNull_returnFalseValue_whenObjectIsNotNull() {
        final String value = If
                .isNull("somevalue")
                .thenReturn("null")
                .elseReturn("non null");

        assertThat(value, is("non null"));
    }

    @Test
    public void ifIsNull_returnValueFromIfSupplier_whenObjectIsNull() {
        final String value = If
                .isNull(null)
                .thenGet(() -> "null")
                .elseReturn("non null");

        assertThat(value, is("null"));
    }

    @Test
    public void ifIsNull_returnValueFromElse_whenObjectIsNotNull() {
        final String value = If
                .isNull("somevalue")
                .thenGet(() -> "null")
                .elseReturn("non null");

        assertThat(value, is("non null"));
    }

    @Test
    public void ifIsNull_returnValueFromMapFunction_whenObjectIsNotNull() {
        final String value = If
                .isNull("somevalue")
                .thenReturn("null")
                .elseMap((some) -> some + " non null");

        assertThat(value, is("somevalue non null"));
    }

    @Test
    public void ifIsNull_returnValueFromIfValue_whenObjectIsNull() {
        final String value = If
                .isNull(null)
                .thenReturn("null")
                .elseMap((some) -> some + " non null");

        assertThat(value, is("null"));
    }

    @Test
    public void ifIsNull_returnValueFromIfMap_whenObjectIsNull() {
        final String value = If
                .isNull(null)
                .thenGet(() -> "null")
                .elseMap((some) -> some + " non null");

        assertThat(value, is("null"));
    }

    @Test
    public void ifIsNull_returnValueFromElseMap_whenObjectIsNotNull() {
        final String value = If
                .isNull("somevalue")
                .thenGet(() -> "null")
                .elseMap((some) -> some + " non null");

        assertThat(value, is("somevalue non null"));
    }

    @Test
    public void ifIsNull_throwException_whenObjectIsNull() {
        expectedException.expect(RuntimeException.class);
        If.isNull(null)
                .thenThrow(() -> new RuntimeException("somevalue"))
                .elseReturn("non null");
    }

    @Test
    public void ifIsNull_returnNonNullValue_whenObjectIsNotNull() {
        final String value = If
                .isNull("somevalue")
                .thenThrow(() -> new RuntimeException("somevalue"))
                .elseReturn("non null");

        assertThat(value, is("non null"));
    }

    @Test
    public void ifIsNull_throwException_whenObjectIsNullAndMappingIsProvided() {
        expectedException.expect(RuntimeException.class);
        If.isNull(null)
                .thenThrow(() -> new RuntimeException("somevalue"))
                .elseMap((v) -> "non null");
    }

    @Test
    public void ifIsNull_returnMappedValue_whenObjectIsNotNull() {
        final String value = If
                .isNull("somevalue")
                .thenThrow(() -> new RuntimeException("somevalue"))
                .elseMap((v) -> v + " non null");

        assertThat(value, is("somevalue non null"));
    }

    @Test
    public void ifIsNull_returnValue_whenObjectIsNull() {
        final String value = If
                .isNull(null)
                .thenReturn("null")
                .elseThrow(RuntimeException::new);

        assertThat(value, is("null"));
    }

    @Test
    public void ifIsNull_throwExceptionWhenReturnFromIf_whenObjectIsNotNull() {
        expectedException.expect(RuntimeException.class);
        If.isNull("not null")
                .thenReturn("somevalue")
                .elseThrow(RuntimeException::new);
    }

    @Test
    public void ifIsNull_returnGetValue_whenObjectIsNull() {
        final String value = If
                .isNull(null)
                .thenGet(() -> "null")
                .elseThrow(RuntimeException::new);

        assertThat(value, is("null"));
    }


    @Test
    public void ifIsNull_throwException_whenObjectIsNotNull() {
        expectedException.expect(RuntimeException.class);
        If.isNull("not null")
                .thenGet(() -> "somevalue")
                .elseThrow(RuntimeException::new);
    }

    @Test
    public void ifIsNull_throwExceptionDefinedInIf_whenObjectIsNull() {
        expectedException.expect(IllegalArgumentException.class);
        If.isNull(null)
                .thenThrow(IllegalArgumentException::new)
                .elseThrow(RuntimeException::new);
    }

    @Test
    public void ifIsNull_throwExceptionDefinedInElse_whenObjectIsNotNull() {
        expectedException.expect(RuntimeException.class);
        If.isNull("some")
                .thenThrow(IllegalArgumentException::new)
                .elseThrow(RuntimeException::new);
    }

    @Test
    public void isTrue_returnValueFromThenGet_whenExpressionIsTrue() {
        Integer value = If.isTrue(true)
                .thenGet(() -> 10)
                .elseGet(() -> 20);

        assertThat(value, is(10));
    }

    @Test
    public void isTrue_returnValueFromElseGet_whenExpressionIsFalse() {
        Integer value = If.isTrue(false)
                .thenGet(() -> 10)
                .elseGet(() -> 20);

        assertThat(value, is(20));
    }

    @Test
    public void isTrue_shouldCallThen_whenExpressionIsTrue() {
        TestHelper testHelper = mock(TestHelper.class);
        If.isTrue(true)
                .thenCall(testHelper::thenCallMe)
                .elseCall(testHelper::elseCallMe);
        verify(testHelper).thenCallMe();
    }

    @Test
    public void isTrue_shouldCallElse_whenExpressionIsFalse() {
        TestHelper testHelper = mock(TestHelper.class);
        If.isTrue(false)
                .thenCall(testHelper::thenCallMe)
                .elseCall(testHelper::elseCallMe);
        verify(testHelper).elseCallMe();
    }

    @Test
    public void isTrue_shouldThrowExceptionInThen_whenExpressionIsTrue() {
        TestHelper testHelper = mock(TestHelper.class);

        expectedException.expect(IllegalArgumentException.class);
        If.isTrue(true)
                .thenThrow(IllegalArgumentException::new)
                .elseCall(testHelper::elseCallMe);
    }

    @Test
    public void isTrue_shouldCallInElse_whenExpressionIsFalse() {
        TestHelper testHelper = mock(TestHelper.class);

        If.isTrue(false)
                .thenThrow(IllegalArgumentException::new)
                .elseCall(testHelper::elseCallMe);
        verify(testHelper).elseCallMe();
    }

    @Test
    public void isTrue_shouldGetInElse_whenExpressionIsFalse() {
        final Integer value = If.isTrue(false)
                .thenThrow(IllegalArgumentException::new)
                .elseGet(() -> 20);
        assertThat(value, is(20));
    }

    @Test
    public void isTrue_throwException_whenExpressionIsTrueAndElseGetIsDefined() {
        expectedException.expect(IllegalArgumentException.class);
        If.isTrue(true)
                .thenThrow(IllegalArgumentException::new)
                .elseGet(() -> 20);
    }

    @Test
    public void isTrue_throwException_whenExpressionIsTrueAndElseHasElseThrow() {
        expectedException.expect(IllegalArgumentException.class);
        If.isTrue(true)
                .thenThrow(IllegalArgumentException::new)
                .elseThrow(RuntimeException::new);
    }

    @Test
    public void isTrue_throwException_whenExpressionIsFalseAndElseAndThenHasThrow() {
        expectedException.expect(RuntimeException.class);
        If.isTrue(false)
                .thenThrow(IllegalArgumentException::new)
                .elseThrow(RuntimeException::new);
    }

    @Test
    public void isTrue_throwException_whenExpressionIsFalseAndThenCallIsDefined() {
        TestHelper testHelper = mock(TestHelper.class);

        expectedException.expect(IllegalArgumentException.class);
        If.isTrue(false)
                .thenCall(testHelper::thenCallMe)
                .elseThrow(IllegalArgumentException::new);
    }

    @Test
    public void isTrue_callMethod_whenExpressionIsTrueAndThenCallIsDefined() {
        TestHelper testHelper = mock(TestHelper.class);

        If.isTrue(true)
                .thenCall(testHelper::thenCallMe)
                .elseThrow(IllegalArgumentException::new);

        verify(testHelper).thenCallMe();
    }

    @Test
    public void isTrue_returnGetMethodResult_whenExpressionIsTrueAndThenGetIsDefined() {
        final Integer value = If.isTrue(true)
                .thenGet(() -> 34)
                .elseThrow(IllegalArgumentException::new);
        assertThat(value, is(34));
    }

    @Test
    public void isTrue_throwException_whenExpressionIsFalse() {
        expectedException.expect(IllegalArgumentException.class);
        If.isTrue(false)
                .thenGet(() -> 34)
                .elseThrow(IllegalArgumentException::new);
    }

    @Test
    public void isFalse_returnValueFromThenGet_whenExpressionIsFalse() {
        Integer value = If.isFalse(true)
                .thenGet(() -> 10)
                .elseGet(() -> 20);

        assertThat(value, is(20));
    }

    @Test
    public void isFalse_returnValueFromElseGet_whenExpressionIsTrue() {
        Integer value = If.isFalse(true)
                .thenGet(() -> 10)
                .elseGet(() -> 20);

        assertThat(value, is(20));
    }

    @Test
    public void isFalse_shouldCallThen_whenExpressionIsFalse() {
        TestHelper testHelper = mock(TestHelper.class);
        If.isFalse(false)
                .thenCall(testHelper::thenCallMe)
                .elseCall(testHelper::elseCallMe);
        verify(testHelper).thenCallMe();
    }

    @Test
    public void isFalse_shouldCallElse_whenExpressionIsTrue() {
        TestHelper testHelper = mock(TestHelper.class);
        If.isFalse(true)
                .thenCall(testHelper::thenCallMe)
                .elseCall(testHelper::elseCallMe);
        verify(testHelper).elseCallMe();
    }

    @Test
    public void isFalse_shouldThrowExceptionInThen_whenExpressionIsFalse() {
        TestHelper testHelper = mock(TestHelper.class);

        expectedException.expect(IllegalArgumentException.class);
        If.isFalse(false)
                .thenThrow(IllegalArgumentException::new)
                .elseCall(testHelper::elseCallMe);
    }

    @Test
    public void isFalse_shouldCallInElse_whenExpressionIsTrue() {
        TestHelper testHelper = mock(TestHelper.class);

        If.isFalse(true)
                .thenThrow(IllegalArgumentException::new)
                .elseCall(testHelper::elseCallMe);
        verify(testHelper).elseCallMe();
    }

    @Test
    public void isFalse_shouldGetInElse_whenExpressionIsTrue() {
        final Integer value = If.isFalse(true)
                .thenThrow(IllegalArgumentException::new)
                .elseGet(() -> 20);
        assertThat(value, is(20));
    }

    @Test
    public void isFalse_throwException_whenExpressionIsFalseAndElseGetIsDefined() {
        expectedException.expect(IllegalArgumentException.class);
        If.isFalse(false)
                .thenThrow(IllegalArgumentException::new)
                .elseGet(() -> 20);
    }

    @Test
    public void isFalse_throwException_whenExpressionIsFalseAndElseHasElseThrow() {
        expectedException.expect(IllegalArgumentException.class);
        If.isFalse(false)
                .thenThrow(IllegalArgumentException::new)
                .elseThrow(RuntimeException::new);
    }

    @Test
    public void isFalse_throwException_whenExpressionIsTrueAndElseAndThenHasThrow() {
        expectedException.expect(RuntimeException.class);
        If.isFalse(true)
                .thenThrow(IllegalArgumentException::new)
                .elseThrow(RuntimeException::new);
    }

    @Test
    public void isFalse_throwException_whenExpressionIsTrueAndThenCallIsDefined() {
        TestHelper testHelper = mock(TestHelper.class);

        expectedException.expect(IllegalArgumentException.class);
        If.isFalse(true)
                .thenCall(testHelper::thenCallMe)
                .elseThrow(IllegalArgumentException::new);
    }

    @Test
    public void isFalse_callMethod_whenExpressionIsFalseAndThenCallIsDefined() {
        TestHelper testHelper = mock(TestHelper.class);

        If.isFalse(false)
                .thenCall(testHelper::thenCallMe)
                .elseThrow(IllegalArgumentException::new);

        verify(testHelper).thenCallMe();
    }

    @Test
    public void isFalse_returnGetMethodResult_whenExpressionIsFalseAndThenGetIsDefined() {
        final Integer value = If.isFalse(false)
                .thenGet(() -> 34)
                .elseThrow(IllegalArgumentException::new);
        assertThat(value, is(34));
    }

    @Test
    public void isFalse_throwException_whenExpressionIsTrue() {
        expectedException.expect(IllegalArgumentException.class);
        If.isFalse(true)
                .thenGet(() -> 34)
                .elseThrow(IllegalArgumentException::new);
    }

    @Test
    public void isTrue_throwException_whenExpressionIsTrue() {
        expectedException.expect(IllegalArgumentException.class);
        If.isTrueThen(true).thenThrow(IllegalArgumentException::new);
    }

    @Test
    public void isTrue_doNotThrowException_whenExpressionIsFalse() {
        If.isTrueThen(false).thenThrow(IllegalArgumentException::new);
    }

    @Test
    public void isTrue_thenCall_whenExpressionIsTrue() {
        TestHelper testHelper = mock(TestHelper.class);

        If.isTrueThen(true).thenCall(testHelper::thenCallMe);

        verify(testHelper).thenCallMe();
    }

    @Test
    public void isTrue_dooNotCall_whenExpressionIsFalse() {
        TestHelper testHelper = mock(TestHelper.class);

        If.isTrueThen(false).thenCall(testHelper::thenCallMe);

        verify(testHelper, times(0)).thenCallMe();
    }

    @Test
    public void isFalseThen_throwException_whenExpressionIsTrue() {
        expectedException.expect(IllegalArgumentException.class);
        If.isFalseThen(false).thenThrow(IllegalArgumentException::new);
    }

    @Test
    public void isFalseThen_doNotThrowException_whenExpressionIsFalse() {
        If.isFalseThen(true).thenThrow(IllegalArgumentException::new);
    }

    @Test
    public void isFalseThen_thenCall_whenExpressionIsTrue() {
        TestHelper testHelper = mock(TestHelper.class);

        If.isFalseThen(false).thenCall(testHelper::thenCallMe);

        verify(testHelper).thenCallMe();
    }

    @Test
    public void isFalseThen_dooNotCall_whenExpressionIsFalse() {
        TestHelper testHelper = mock(TestHelper.class);

        If.isFalseThen(true).thenCall(testHelper::thenCallMe);

        verify(testHelper, times(0)).thenCallMe();
    }

    @Test
    public void ifOrElse_returnTrueSupplierResult_whenSupplierEvaluatesToTrue() {

        final Integer value = If.orElse(() -> true, () -> 10, () -> 20);

        assertThat(value, is(10));
    }

    @Test
    public void nullOrElse_returnTrueSupplierResult_whenIsNull() {

        final Integer value = If.nullOrElse(null, () -> 10, () -> 20);

        assertThat(value, is(10));
    }

    @Test
    public void nullOrElse_returnFalseSupplierResult_whenNonNull() {

        final Integer value = If.nullOrElse("202", () -> 10, () -> 20);

        assertThat(value, is(20));
    }

    @Test
    public void ifOrElse_returnFalseSupplierResult_whenSupplierEvaluatesToFalse() {

        final Integer value = If.orElse(() -> false, () -> 10, () -> 20);

        assertThat(value, is(20));
    }

    @Test
    public void ifOrElse_callTrueSupplier_whenSupplierEvaluatesToTrue() {
        TestHelper testHelper = mock(TestHelper.class);

        If.orElse(() -> true, testHelper::thenCallMe, testHelper::elseCallMe);

        verify(testHelper).thenCallMe();
    }

    @Test
    public void ifOrElse_callFalseSupplierResult_whenSupplierEvaluatesToFalse() {
        TestHelper testHelper = mock(TestHelper.class);

        If.orElse(() -> false, testHelper::thenCallMe, testHelper::elseCallMe);

        verify(testHelper).elseCallMe();
    }

    @Test
    public void ifTrue_callSupplier_whenSupplierEvaluates() {
        TestHelper testHelper = mock(TestHelper.class);

        If.isTrueThen(() -> true, testHelper::thenCallMe);

        verify(testHelper).thenCallMe();
    }

    @Test
    public void isNull_thenCall_whenObjectIsNull() {
        TestHelper testHelper = mock(TestHelper.class);

        If.isNullThen(null, testHelper::thenCallMe);

        verify(testHelper).thenCallMe();
    }

    @Test
    public void isNull_doNotCall_whenObjectIsNonNull() {
        TestHelper testHelper = mock(TestHelper.class);

        If.isNullThen("something", testHelper::thenCallMe);

        verify(testHelper, times(0)).thenCallMe();
    }

    @Test
    public void nullOrElse_thenReturnValueFunction_whenObjectIsNonNull() {
        Integer value = If.nullOrElse("1000", () -> 100, Integer::valueOf);

        assertThat(value, is(1000));
    }

    @Test
    public void nullOrElse_thenReturnValue_whenObjectIsNull() {
        String conditionValue = null;
        Integer value = If.nullOrElse(conditionValue, () -> 100, Integer::valueOf);

        assertThat(value, is(100));
    }

    @Test
    public void ifTrue_doesNotCallSupplier_whenSupplierEvaluates() {
        TestHelper testHelper = mock(TestHelper.class);

        If.isTrueThen(() -> false, testHelper::thenCallMe);

        verify(testHelper, times(0)).thenCallMe();
    }

    @Test
    public void ifOrElse_returnTrueSupplierResult_whenExpressionEvaluatesToTrue() {

        final Integer value = If.orElse(true, () -> 10, () -> 20);

        assertThat(value, is(10));
    }

    @Test
    public void ifOrElse_returnFalseSupplierResult_whenExpressionEvaluatesToFalse() {

        final Integer value = If.orElse(false, () -> 10, () -> 20);

        assertThat(value, is(20));
    }

    @Test
    public void ifOrElse_returnThenReturnValue_whenExpressionEvaluatesToTrue() {

        final Integer value = If.orElse(true, 10, 20);

        assertThat(value, is(10));
    }

    @Test
    public void ifOrElse_returnElseReturnValue_whenExpressionEvaluatesToFalse() {

        final Integer value = If.orElse(false, 10, 20);

        assertThat(value, is(20));
    }

    @Test
    public void ifOrElse_returnThenReturnValue_whenSupplierIsTrue() {

        final Integer value = If.orElse(() -> true, 10, 20);

        assertThat(value, is(10));
    }

    @Test
    public void ifOrElse_returnElseReturnValue_whenSupplierIsFalse() {

        final Integer value = If.orElse(() -> false, 10, 20);

        assertThat(value, is(20));
    }

    @Test
    public void ifOrElse_callTrueSupplier_whenExpressionEvaluatesToTrue() {
        TestHelper testHelper = mock(TestHelper.class);

        If.orElse(true, testHelper::thenCallMe, testHelper::elseCallMe);

        verify(testHelper).thenCallMe();
    }

    @Test
    public void ifOrElse_callFalseSupplierResult_whenExpressionEvaluatesToFalse() {
        TestHelper testHelper = mock(TestHelper.class);

        If.orElse(false, testHelper::thenCallMe, testHelper::elseCallMe);

        verify(testHelper).elseCallMe();
    }

    @Test
    public void ifTrue_callSupplier_whenExpressionEvaluatesToTrue() {
        TestHelper testHelper = mock(TestHelper.class);

        If.isTrueThen(true, testHelper::thenCallMe);

        verify(testHelper).thenCallMe();
    }

    @Test
    public void ifTrue_doesNotCallSupplier_whenExpressionEvaluatesToFalse() {
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

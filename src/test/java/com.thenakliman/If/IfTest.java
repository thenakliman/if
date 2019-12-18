package com.thenakliman.If;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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
        final String value = If
                .isNull(null)
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
        final String value = If
                .isNull(null)
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
    public void expressionIsTrue_returnValueFromThenGet_whenExpressionIsTrue() {
        Integer value = If.expressionIsTrue(true)
                .thenGet(() -> 10)
                .elseGet(() -> 20);

        assertThat(value, is(10));
    }

    @Test
    public void expressionIsTrue_returnValueFromElseGet_whenExpressionIsFalse() {
        Integer value = If.expressionIsTrue(false)
                .thenGet(() -> 10)
                .elseGet(() -> 20);

        assertThat(value, is(20));
    }

    @Test
    public void expressionIsTrue_shouldCallThen_whenExpressionIsTrue() {
        TestHelper testHelper = mock(TestHelper.class);
        If.expressionIsTrue(true)
                .thenCall(testHelper::thenCallMe)
                .elseCall(testHelper::elseCallMe);
        verify(testHelper).thenCallMe();
    }

    @Test
    public void expressionIsTrue_shouldCallElse_whenExpressionIsFalse() {
        TestHelper testHelper = mock(TestHelper.class);
        If.expressionIsTrue(false)
                .thenCall(testHelper::thenCallMe)
                .elseCall(testHelper::elseCallMe);
        verify(testHelper).elseCallMe();
    }

    @Test
    public void expressionIsTrue_shouldThrowExceptionInThen_whenExpressionIsTrue() {
        TestHelper testHelper = mock(TestHelper.class);

        expectedException.expect(IllegalArgumentException.class);
        If.expressionIsTrue(true)
                .thenThrow(IllegalArgumentException::new)
                .elseCall(testHelper::elseCallMe);
    }

    @Test
    public void expressionIsTrue_shouldCallInElse_whenExpressionIsFalse() {
        TestHelper testHelper = mock(TestHelper.class);

        If.expressionIsTrue(false)
                .thenThrow(IllegalArgumentException::new)
                .elseCall(testHelper::elseCallMe);
        verify(testHelper).elseCallMe();
    }

    @Test
    public void expressionIsTrue_shouldGetInElse_whenExpressionIsFalse() {
        final Integer value = If.expressionIsTrue(false)
                .thenThrow(IllegalArgumentException::new)
                .elseGet(() -> 20);
        assertThat(value, is(20));
    }

    @Test
    public void expressionIsTrue_throwException_whenExpressionIsTrueAndElseGetIsDefined() {
        expectedException.expect(IllegalArgumentException.class);
        If.expressionIsTrue(true)
                .thenThrow(IllegalArgumentException::new)
                .elseGet(() -> 20);
    }

    @Test
    public void expressionIsTrue_throwException_whenExpressionIsTrueAndElseHasElseThrow() {
        expectedException.expect(IllegalArgumentException.class);
        If.expressionIsTrue(true)
                .thenThrow(IllegalArgumentException::new)
                .thenThrow(RuntimeException::new);
    }

    @Test
    public void expressionIsTrue_throwException_whenExpressionIsFalseAndElseAndThenHasThrow() {
        expectedException.expect(RuntimeException.class);
        If.expressionIsTrue(false)
                .thenThrow(IllegalArgumentException::new)
                .thenThrow(RuntimeException::new);
    }

    @Test
    public void expressionIsTrue_throwException_whenExpressionIsFalseAndThenCallIsDefined() {
        TestHelper testHelper = mock(TestHelper.class);

        expectedException.expect(IllegalArgumentException.class);
        If.expressionIsTrue(false)
                .thenCall(testHelper::thenCallMe)
                .elseThrow(IllegalArgumentException::new);
    }

    @Test
    public void expressionIsTrue_callMethod_whenExpressionIsTrueAndThenCallIsDefined() {
        TestHelper testHelper = mock(TestHelper.class);

        If.expressionIsTrue(true)
                .thenCall(testHelper::thenCallMe)
                .elseThrow(IllegalArgumentException::new);

        verify(testHelper).thenCallMe();
    }

    @Test
    public void expressionIsTrue_returnGetMethodResult_whenExpressionIsTrueAndThenGetIsDefined() {
        final Integer value = If.expressionIsTrue(true)
                .thenGet(() -> 34)
                .elseThrow(IllegalArgumentException::new);
        assertThat(value, is(34));
    }

    @Test
    public void expressionIsTrue_throwException_whenExpressionIsFalse() {
        expectedException.expect(IllegalArgumentException.class);
        If.expressionIsTrue(false)
                .thenGet(() -> 34)
                .elseThrow(IllegalArgumentException::new);
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

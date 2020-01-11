package com.thenakliman.ifs;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class NullTernaryTest {
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
}

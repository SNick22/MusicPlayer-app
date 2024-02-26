package com.azat.auth.domain.validators

import org.junit.Assert.*
import org.junit.Test

class CodeValidatorTest {

    private val codeValidator = CodeValidator()

    @Test
    fun `should validate a string from digits and with length 4`() {
        assertEquals(true, codeValidator.invoke("1234").successful)
    }

    @Test
    fun `shouldn't validate a string from not digits`() {
        assertEquals(false, codeValidator.invoke("abcd").successful)
        assertEquals(false, codeValidator.invoke("    ").successful)
    }

    @Test
    fun `shouldn't validate a string with length different from 4`() {
        assertEquals(false, codeValidator.invoke("").successful)
        assertEquals(false, codeValidator.invoke("12345").successful)
        assertEquals(false, codeValidator.invoke("123").successful)
    }
}

package com.azat.auth.domain.validators

import org.junit.Assert.*
import org.junit.Test

class PasswordValidatorTest {

    private val passwordValidator = PasswordValidator()

    @Test
    fun `should validate a password with length greater or equal 8`() {
        assertEquals(true, passwordValidator.invoke("12345678").successful)
        assertEquals(true, passwordValidator.invoke("123456789").successful)
    }

    @Test
    fun `shouldn't validate a password with length less than 8`() {
        assertEquals(false, passwordValidator.invoke("").successful)
        assertEquals(false, passwordValidator.invoke("fdgdgd").successful)
    }
}

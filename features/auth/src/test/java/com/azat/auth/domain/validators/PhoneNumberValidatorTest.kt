package com.azat.auth.domain.validators

import org.junit.Assert.*
import org.junit.Test

class PhoneNumberValidatorTest {

    private val phoneNumberValidator = PhoneNumberValidator()

    @Test
    fun `should validate a string from digits and with length 10`() {
        assertEquals(true, phoneNumberValidator.invoke("9373606464").successful)
    }

    @Test
    fun `shouldn't validate a string from not digits`() {
        assertEquals(false, phoneNumberValidator.invoke("asdfghjkl;").successful)
        assertEquals(false, phoneNumberValidator.invoke("          ").successful)
    }

    @Test
    fun `shouldn't validate a string with length different from 10`() {
        assertEquals(false, phoneNumberValidator.invoke("").successful)
        assertEquals(false, phoneNumberValidator.invoke("12345").successful)
        assertEquals(false, phoneNumberValidator.invoke("12356756757fh").successful)
    }
}

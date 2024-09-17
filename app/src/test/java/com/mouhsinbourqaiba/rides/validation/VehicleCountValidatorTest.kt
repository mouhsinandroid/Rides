package com.mouhsinbourqaiba.rides.validation

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class VehicleCountValidatorTest {

    private lateinit var validator: VehicleCountValidator

    @Before
    fun setup() {
        validator = VehicleCountValidator()
    }

    @Test
    fun `validate should return Valid for input between 1 and 100`() {
        val result = validator.validate("50")
        assertTrue(result is ValidationResult.Valid)
        assertEquals(50, (result as ValidationResult.Valid).count)
    }

    @Test
    fun `validate should return Invalid for input below 1`() {
        val result = validator.validate("0")
        assertTrue(result is ValidationResult.Invalid)
        assertEquals("Please enter a number between 1 and 100", (result as ValidationResult.Invalid).errorMessage)
    }

    @Test
    fun `validate should return Invalid for input above 100`() {
        val result = validator.validate("101")
        assertTrue(result is ValidationResult.Invalid)
        assertEquals("Please enter a number between 1 and 100", (result as ValidationResult.Invalid).errorMessage)
    }

    @Test
    fun `validate should return Invalid for non-numeric input`() {
        val result = validator.validate("abc")
        assertTrue(result is ValidationResult.Invalid)
        assertEquals("Please enter a valid number", (result as ValidationResult.Invalid).errorMessage)
    }
}

package com.mouhsinbourqaiba.rides.validation

class VehicleCountValidator {
    fun validate(input: String): ValidationResult {
        val count = input.toIntOrNull()
        return when {
            count == null -> ValidationResult.Invalid("Please enter a valid number")
            count !in 1..100 -> ValidationResult.Invalid("Please enter a number between 1 and 100")
            else -> ValidationResult.Valid(count)
        }
    }
}
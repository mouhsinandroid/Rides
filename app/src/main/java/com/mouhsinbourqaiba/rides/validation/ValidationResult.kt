package com.mouhsinbourqaiba.rides.validation

sealed class ValidationResult {
    data class Valid(val count: Int) : ValidationResult()
    data class Invalid(val errorMessage: String) : ValidationResult()
}
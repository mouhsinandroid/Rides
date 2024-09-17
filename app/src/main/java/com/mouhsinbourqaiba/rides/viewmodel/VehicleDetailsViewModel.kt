package com.mouhsinbourqaiba.rides.viewmodel

import androidx.lifecycle.ViewModel
import com.mouhsinbourqaiba.rides.model.Vehicle

class VehicleDetailsViewModel : ViewModel() {
    var vehicle: Vehicle? = null
        private set

    var kilometrage: Int = 0

    fun setVehicle(vehicle: Vehicle) {
        this.vehicle = vehicle
    }

    fun estimateCarboneEmissions(kilometrage: Int): Double {
        val baseUnit = 1.0
        val additionalUnit = 1.5
        val threshold = 5000

        return if (kilometrage <= threshold) {
            baseUnit * kilometrage
        } else {
            val baseEmissions = baseUnit * threshold
            val additionalEmissions = additionalUnit * (kilometrage - threshold)
            baseEmissions + additionalEmissions
        }
    }
}

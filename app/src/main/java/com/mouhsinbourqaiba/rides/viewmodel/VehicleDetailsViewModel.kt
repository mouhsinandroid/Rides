package com.mouhsinbourqaiba.rides.viewmodel

import androidx.lifecycle.ViewModel
import com.mouhsinbourqaiba.rides.model.Vehicle

class VehicleDetailsViewModel : ViewModel() {
    var vehicle: Vehicle? = null
        private set

    fun setVehicle(vehicle: Vehicle) {
        this.vehicle = vehicle
    }
}

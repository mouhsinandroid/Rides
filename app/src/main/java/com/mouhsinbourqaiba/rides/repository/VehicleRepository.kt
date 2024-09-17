package com.mouhsinbourqaiba.rides.repository

import com.mouhsinbourqaiba.rides.model.Vehicle
import com.mouhsinbourqaiba.rides.network.VehicleClient

class VehicleRepository {
    suspend fun getRandomVehicles(size: Int): List<Vehicle> {
        return VehicleClient.api.getRandomVehicles(size)
    }
}

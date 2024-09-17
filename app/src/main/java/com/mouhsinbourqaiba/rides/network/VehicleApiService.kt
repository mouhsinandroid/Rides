package com.mouhsinbourqaiba.rides.network

import com.mouhsinbourqaiba.rides.model.Vehicle
import retrofit2.http.GET
import retrofit2.http.Query

interface VehicleApiService {
    @GET("api/vehicle/random_vehicle")
    suspend fun getRandomVehicles(@Query("size") size: Int): List<Vehicle>
}

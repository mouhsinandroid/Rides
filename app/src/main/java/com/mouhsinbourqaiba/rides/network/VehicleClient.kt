package com.mouhsinbourqaiba.rides.network

object VehicleClient {

    private const val BASE_URL = "https://random-data-api.com/"

    val api: VehicleApiService by lazy {
        retrofit2.Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .build()
            .create(VehicleApiService::class.java)
    }
}
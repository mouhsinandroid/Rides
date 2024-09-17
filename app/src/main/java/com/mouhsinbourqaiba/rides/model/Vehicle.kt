package com.mouhsinbourqaiba.rides.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Vehicle(
    val id: Int,
    val kilometrage: Int,
    val mileage: Int,
    val specs: List<String>,
    val transmission: String,
    val uid: String,
    val vin: String,
    val color: String,
    val doors: Int,
    @SerializedName("car_options")
    val carOptions: List<String>,
    @SerializedName("car_type")
    val carType: String,
    @SerializedName("drive_type")
    val driveType: String,
    @SerializedName("fuel_type")
    val fuelType: String,
    @SerializedName("license_plate")
    val licensePlate: String,
    @SerializedName("make_and_model")
    val makeAndModel: String
): Parcelable
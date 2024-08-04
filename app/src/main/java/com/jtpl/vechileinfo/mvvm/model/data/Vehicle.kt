package com.jtpl.vechileinfo.mvvm.model.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Vehicle(
    @SerialName("vehicle_type")
    val vehicleType: String,
    @SerialName("make")
    val make: String,
    @SerialName("model")
    val model: String,
    @SerialName("year")
    val year: String,
    @SerialName("fuel_type")
    val fuelType: String,
    @SerialName("capacity")
    val capacity: String,
    @SerialName("imei")
    val imei: String,
    @SerialName("vehicle_details")
    val etVehicleDetails: String,
    @SerialName("registration_number")
    val etRegistrationNumber: String
) : java.io.Serializable

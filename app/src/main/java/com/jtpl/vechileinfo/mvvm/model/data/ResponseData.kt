package com.jtpl.vechileinfo.mvvm.model.data

data class ResponseData(
    val status: Int,
    val message: String,
    val vehicle_type: List<VehicleType>
)

data class VehicleType(
    val text: String,
    val value: Int,
    val images: String
)

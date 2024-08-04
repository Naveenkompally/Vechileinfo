package com.jtpl.vechileinfo.mvvm.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.jtpl.vechileinfo.mvvm.model.data.RequestData
import com.jtpl.vechileinfo.mvvm.model.data.ResponseData
import com.jtpl.vechileinfo.mvvm.model.data.VehicleType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VehicleRepository {

    private val apiInterface: ApiInterface = ApiClient.create()

    fun getVehicleTypes(): LiveData<Result<List<VehicleType>>> {
        val vehicleTypes = MutableLiveData<Result<List<VehicleType>>>()
        val requestData = RequestData(11, 1007, "9889897789", 3476)
        val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), Gson().toJson(requestData))

        apiInterface.getVehicleConfigValues(requestBody).enqueue(object : Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                if (response.isSuccessful) {
                    response.body()?.vehicle_type?.let { vehicleTypeList ->
                        val nonNullVehicleTypes = vehicleTypeList.filterNotNull()
                        vehicleTypes.value = Result.success(nonNullVehicleTypes)
                    } ?: run {
                        vehicleTypes.value = Result.failure(Exception("Vehicle type list is null"))
                    }
                } else {
                    vehicleTypes.value = Result.failure(Exception("Failed to fetch vehicle types"))
                }
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                vehicleTypes.value = Result.failure(t)
            }
        })
        return vehicleTypes
    }
}

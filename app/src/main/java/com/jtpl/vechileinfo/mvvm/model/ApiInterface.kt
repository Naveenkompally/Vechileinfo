package com.jtpl.vechileinfo.mvvm.model

import com.jtpl.vechileinfo.mvvm.model.data.ResponseData
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    @POST("jhsmobileapi/mobile/configure/v1/task")
    fun getVehicleConfigValues(@Body requestBody: RequestBody): Call<ResponseData>
}

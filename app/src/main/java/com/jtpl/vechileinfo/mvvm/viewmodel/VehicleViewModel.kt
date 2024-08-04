package com.jtpl.vechileinfo.mvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jtpl.vechileinfo.mvvm.model.VehicleRepository
import com.jtpl.vechileinfo.mvvm.model.data.VehicleType

class VehicleViewModel(application: Application) : AndroidViewModel(application) {

    private val vehicleRepository = VehicleRepository()
    private val _vehicleTypes = MutableLiveData<Result<List<VehicleType>>>()
    val vehicleTypes: LiveData<Result<List<VehicleType>>> = _vehicleTypes

    init {
        fetchVehicleTypes()
    }

    fun fetchVehicleTypes() {
        _vehicleTypes.value = Result.success(emptyList())
        vehicleRepository.getVehicleTypes().observeForever { result ->
            _vehicleTypes.postValue(result)
        }
    }
}


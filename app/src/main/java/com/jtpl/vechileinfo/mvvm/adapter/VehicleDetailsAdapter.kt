package com.jtpl.vechileinfo.mvvm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jtpl.vechileinfo.R
import com.jtpl.vechileinfo.mvvm.model.data.Vehicle

class VehicleDetailsAdapter(private val vehicleList: List<Vehicle>) :
    RecyclerView.Adapter<VehicleDetailsAdapter.VehicleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vehicle_detail, parent, false)
        return VehicleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        val vehicle = vehicleList[position]
        holder.bind(vehicle)
    }

    override fun getItemCount(): Int = vehicleList.size

    class VehicleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvVehicleType: TextView = itemView.findViewById(R.id.tvVehicleType)
        private val tvMake: TextView = itemView.findViewById(R.id.tvMake)
        private val tvModel: TextView = itemView.findViewById(R.id.tvModel)
        private val tvYear: TextView = itemView.findViewById(R.id.tvYear)
        private val tvFuelType: TextView = itemView.findViewById(R.id.tvFuelType)
        private val tvCapacity: TextView = itemView.findViewById(R.id.tvCapacity)
        private val tvImei: TextView = itemView.findViewById(R.id.tvImei)
        private val etVehicleTag: TextView = itemView.findViewById(R.id.etVehicleTag)
        private val etVehicleRegistration: TextView = itemView.findViewById(R.id.etVehicleRegistration)

        fun bind(vehicle: Vehicle) {
            tvVehicleType.text = vehicle.vehicleType
            tvMake.text = vehicle.make
            tvModel.text = vehicle.model
            tvYear.text = vehicle.year
            tvFuelType.text = vehicle.fuelType
            tvCapacity.text = vehicle.capacity
            tvImei.text = vehicle.imei
            etVehicleTag.text = vehicle.etVehicleDetails
            etVehicleRegistration.text = vehicle.etRegistrationNumber
        }
    }
}

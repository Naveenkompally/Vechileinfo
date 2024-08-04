package com.jtpl.vechileinfo.mvvm.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jtpl.vechileinfo.R
import com.jtpl.vechileinfo.mvvm.adapter.VehicleDetailsAdapter
import com.jtpl.vechileinfo.mvvm.model.data.Vehicle

class VehicleDetailsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var ivBack: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_details)

        recyclerView = findViewById(R.id.recyclerView)
        ivBack = findViewById(R.id.ivBack)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val vehicleDetails = intent.getSerializableExtra("vehicleDetails") as? Vehicle
        vehicleDetails?.let {
            val adapter = VehicleDetailsAdapter(listOf(it))
            recyclerView.adapter = adapter
            listener()
        }
    }

    private fun listener() {
        ivBack.setOnClickListener {
            startActivity(Intent(this, AddVehicleActivity::class.java))
            finish()
        }
    }
}

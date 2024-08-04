package com.jtpl.vechileinfo.mvvm.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.zxing.integration.android.IntentIntegrator
import com.jtpl.vechileinfo.R
import com.jtpl.vechileinfo.mvvm.model.data.Vehicle
import com.jtpl.vechileinfo.mvvm.model.data.VehicleType
import com.jtpl.vechileinfo.mvvm.viewmodel.VehicleViewModel

class AddVehicleActivity : AppCompatActivity() {

    private lateinit var viewModel: VehicleViewModel
    private lateinit var spinnerVehicleType: Spinner
    private lateinit var spinnerVehicleMake: Spinner
    private lateinit var spinnerVehicleModel: Spinner
    private lateinit var spinnerManufactureYear: Spinner
    private lateinit var spinnerFuelType: Spinner
    private lateinit var spinnerCapacity: Spinner
    private lateinit var etImei: EditText
    private lateinit var etVehicleTag: EditText
    private lateinit var etRegistrationNumber: EditText
    private lateinit var ivQrCode: ImageView
    private lateinit var refresh: ImageView
    private lateinit var ivBack: ImageView
    private lateinit var btnMore: ImageView
    private lateinit var btnAdd: Button
    private lateinit var progressBar: ProgressBar
    private var isExpanded = false
    private lateinit var vehicleTypeContainer: LinearLayout

    private lateinit var input1: EditText
    private lateinit var input2: EditText
    private lateinit var input3: EditText
    private lateinit var input4: EditText
    private lateinit var input5: EditText
    private lateinit var input6: EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(VehicleViewModel::class.java)

        spinnerVehicleType = findViewById(R.id.spinnerCapacity)
        spinnerVehicleMake = findViewById(R.id.spinnerMake)
        spinnerVehicleModel = findViewById(R.id.spinnerModel)
        spinnerManufactureYear = findViewById(R.id.spinnerYear)
        spinnerFuelType = findViewById(R.id.spinnerFuelType)
        spinnerCapacity = findViewById(R.id.spinnerCapacity)
        etImei = findViewById(R.id.etImei)
        ivBack = findViewById(R.id.ivBack)
        etVehicleTag = findViewById(R.id.etVehicle_details)
        etRegistrationNumber = findViewById(R.id.etRegistration_number)
        refresh = findViewById(R.id.refresh)
        ivQrCode = findViewById(R.id.iv_qr_code)
        btnMore = findViewById(R.id.btn_more)
        btnAdd = findViewById(R.id.btn_add)
        progressBar = findViewById(R.id.progressBar)
        vehicleTypeContainer = findViewById(R.id.vehicleTypeContainer)

        input1 = findViewById(R.id.input1)
        input2 = findViewById(R.id.input2)
        input3 = findViewById(R.id.input3)
        input4 = findViewById(R.id.input4)
        input5 = findViewById(R.id.input5)
        input6 = findViewById(R.id.input6)

        setSpinnerAdapter(spinnerVehicleMake, R.array.vehicle_makes)
        setSpinnerAdapter(spinnerVehicleModel, R.array.vehicle_models)
        setSpinnerAdapter(spinnerManufactureYear, R.array.manufacture_years)
        setSpinnerAdapter(spinnerFuelType, R.array.fuel_types)
        setSpinnerAdapter(spinnerCapacity, R.array.capacities)

        observer()
        listener()
    }

    private fun observer() {
        viewModel.vehicleTypes.observe(this, Observer { result ->
            result.onSuccess { vehicleTypes ->
                vehicleTypes?.let {
                    val nonNullVehicleTypes = it.filterNotNull()
                    val adapter = ArrayAdapter(this,
                        android.R.layout.simple_spinner_item,
                        nonNullVehicleTypes.map { vehicleType -> vehicleType.text })
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerVehicleType.adapter = adapter
                    setupVehicleTypeImages(nonNullVehicleTypes)
                }
            }
            result.onFailure {
                Toast.makeText(this, "Failed to load vehicle types", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun listener() {
        ivQrCode.setOnClickListener {
            startQrScanner()
        }
        ivBack.setOnClickListener{
            finish()
        }
        btnMore.setOnClickListener { v -> toggleVehicleTypes(v) }
        btnAdd.setOnClickListener {
            val selectedVehicleType = spinnerVehicleType.selectedItem?.toString()
            val selectedMake = spinnerVehicleMake.selectedItem?.toString()
            val selectedModel = spinnerVehicleModel.selectedItem?.toString()
            val selectedYear = spinnerManufactureYear.selectedItem?.toString()
            val selectedFuelType = spinnerFuelType.selectedItem?.toString()
            val selectedCapacity = spinnerCapacity.selectedItem?.toString()
            val imei = etImei.text.toString()
            val etVehicleTag = etVehicleTag.text.toString()
            val etRegistrationNumber = etRegistrationNumber.text.toString()

            if (selectedVehicleType.isNullOrEmpty() || selectedMake.isNullOrEmpty() || selectedModel.isNullOrEmpty() || selectedYear.isNullOrEmpty() || selectedFuelType.isNullOrEmpty() || selectedCapacity.isNullOrEmpty() || imei.isNullOrEmpty() || etVehicleTag.isNullOrEmpty() || etRegistrationNumber.isNullOrEmpty()) {
                Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
            } else {
                val newVehicle = Vehicle(
                    selectedVehicleType,
                    selectedMake,
                    selectedModel,
                    selectedYear,
                    selectedFuelType,
                    selectedCapacity,
                    imei,
                    etVehicleTag,
                    etRegistrationNumber

                )
                val intent = Intent(this, VehicleDetailsActivity::class.java)
                intent.putExtra("vehicleDetails", newVehicle)
                startActivity(intent)
            }
        }
        refresh.setOnClickListener {
            refreshFields()
        }
        ivBack
    }

    private fun refreshFields() {
        spinnerVehicleType.setSelection(0)
        spinnerVehicleMake.setSelection(0)
        spinnerVehicleModel.setSelection(0)
        spinnerManufactureYear.setSelection(0)
        spinnerFuelType.setSelection(0)
        spinnerCapacity.setSelection(0)
        etImei.text.clear()
        input1.text.clear()
        input2.text.clear()
        input3.text.clear()
        input4.text.clear()
        input5.text.clear()
        input6.text.clear()
        etVehicleTag.text.clear()
        etRegistrationNumber.text.clear()
    }

    private fun startQrScanner() {
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setPrompt("Scan a QR code")
        integrator.setCameraId(0)
        integrator.setBeepEnabled(true)
        integrator.setBarcodeImageEnabled(true)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                etImei.setText(result.contents)
                val imei = result.contents
                if (imei.length >= 6) {
                    input1.setText(imei[imei.length - 6].toString())
                    input2.setText(imei[imei.length - 5].toString())
                    input3.setText(imei[imei.length - 4].toString())
                    input4.setText(imei[imei.length - 3].toString())
                    input5.setText(imei[imei.length - 2].toString())
                    input6.setText(imei[imei.length - 1].toString())
                } else {
                    Toast.makeText(this, "Invalid IMEI length", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun setSpinnerAdapter(spinner: Spinner, arrayResId: Int) {
        ArrayAdapter.createFromResource(
            this, arrayResId, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    private fun setupVehicleTypeImages(vehicleTypes: List<VehicleType>) {
        progressBar.visibility = View.VISIBLE
        vehicleTypeContainer.visibility = View.GONE
        vehicleTypeContainer.removeAllViews()

        vehicleTypes.forEachIndexed { index, vehicleType ->
            val itemLayout = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    marginStart = 8.dp
                    marginEnd = 8.dp
                }
            }

            val imageView = ImageView(this).apply {
                layoutParams = LinearLayout.LayoutParams(40.dp, 40.dp)
                setImageResource(R.drawable.delivery)
                visibility = if (index < 3) View.VISIBLE else View.GONE
            }

            val textView = TextView(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
                )
                text = vehicleType.text
                visibility = if (index < 3) View.VISIBLE else View.GONE
                gravity = android.view.Gravity.CENTER
            }

            itemLayout.addView(imageView)
            itemLayout.addView(textView)
            vehicleTypeContainer.addView(itemLayout)
        }

        progressBar.visibility = View.GONE
        vehicleTypeContainer.visibility = View.VISIBLE
    }

    private val Int.dp: Int
        get() = (this * resources.displayMetrics.density).toInt()

    fun toggleVehicleTypes(view: View) {
        val visible = !isExpanded
        vehicleTypeContainer.children.forEachIndexed { index, child ->
            if (child is LinearLayout) {
                child.children.forEach { innerChild ->
                    if (index < 3) {
                        innerChild.visibility = View.VISIBLE
                    } else {
                        innerChild.visibility = if (visible) View.VISIBLE else View.GONE
                    }
                }
            }
        }
        btnMore.setImageResource(if (visible) R.drawable.ic_remove else R.drawable.ic_add)
        isExpanded = visible
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}

package com.xp.xperiencurity.xperiencurity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_device.*
import kotlinx.android.synthetic.main.activity_check_for_updates.*
import kotlinx.android.synthetic.main.activity_main.*

class AddDevice : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    var type = arrayOf("Alarm", "Camera", "Light", "Speaker", "TV", "Thermostat")
    private lateinit var devicen : String
    private lateinit var deviceb : String
    private var empty = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_device)

        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, type)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        with(devicetype) {
            adapter = aa
            setSelection(-1, false)
            onItemSelectedListener = this@AddDevice
            prompt = "Please select the type of device"
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        showToast(message = "Nothing selected")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        saveToFirebaseDatabase(position)
    }

    private fun showToast(context: Context = applicationContext, message: String, duration: Int = Toast.LENGTH_LONG) {
        Toast.makeText(context, message, duration).show()
    }

    private fun saveToFirebaseDatabase(position: Int) {
        savebutton.setOnClickListener {
            fetchUserInput()
            val ref = FirebaseDatabase.getInstance().getReference("Device")
            empty = isEmpty(devicen) && isEmpty(deviceb)

            if (position == 0) {
                val adddevice = Adddevice_Alarm(devicename.text.toString(), devicebrand.text.toString(), true, "Alarm", devicedesc.text.toString(), 10.1)
                if (empty) {
                    showToast(message = "Device name and brand must not be blank")
                }
                else {
                    ref.push().setValue(adddevice)
                    showToast(message = "Device(Alarm) has been added")
                }
            }

            else if (position == 1) {
                val adddevice = Adddevice_Camera(devicename.text.toString(), devicebrand.text.toString(), true, true, "Camera", devicedesc.text.toString(), 4.2)
                if (empty) {
                    showToast(message = "Device name and brand must not be blank")
                }
                else {
                    ref.push().setValue(adddevice)
                    showToast(message = "Device(Camera) has been added")
                }
            }

            else if (position == 2) {
                val adddevice = Adddevice_Light(devicename.text.toString(), devicebrand.text.toString(), true, true, "Light", devicedesc.text.toString(), 1.5)
                if (empty) {
                    showToast(message = "Device name and brand must not be blank")
                }
                else {
                    ref.push().setValue(adddevice)
                    showToast(message = "Device(Light) has been added")
                }
            }

            else if (position == 3) {
                val adddevice = Adddevice_Speaker(devicename.text.toString(), devicebrand.text.toString(), true, true, "Speaker", devicedesc.text.toString(), 3.2)
                if (empty) {
                    showToast(message = "Device name and brand must not be blank")
                }
                else {
                    ref.push().setValue(adddevice)
                    showToast(message = "Device(Speaker) has been added")
                }
            }

            else if (position == 4) {
                val adddevice = Adddevice_TV(devicename.text.toString(), devicebrand.text.toString(), true, true, true, "TV", devicedesc.text.toString(), 2.9)
                if (empty) {
                    showToast(message = "Device name and brand must not be blank")
                }
                else {
                    ref.push().setValue(adddevice)
                    showToast(message = "Device(TV) has been added")
                }
            }

            else if (position == 5) {
                val adddevice = Adddevice_Thermostat(devicename.text.toString(), devicebrand.text.toString(), true, "Thermostat", devicedesc.text.toString(), 4.2)
                if (empty) {
                    showToast(message = "Device name and brand must not be blank")
                }
                else {
                    ref.push().setValue(adddevice)
                    showToast(message = "Device(Thermostat) has been added")
                }
            }
        }
    }

    private fun fetchUserInput() {
        try {
            devicen = devicename.text.toString()
            deviceb = devicebrand.text.toString()
        } catch (e: Exception) {
            Toast.makeText(this, "There is one or more that are not filled in!", Toast.LENGTH_SHORT).show()
        }
    }

    class Adddevice_Alarm(val Name: String, val Brand: String, val Data_logging: Boolean, val Type: String, val Desc: String, val Version: Double)
    class Adddevice_Camera(val Name: String, val Brand: String, val Data_logging: Boolean, val Location_tracking: Boolean, val Type: String, val Desc: String, val Version: Double)
    class Adddevice_Light(val Name: String, val Brand: String, val Data_logging: Boolean, val History_tracking: Boolean, val Type: String, val Desc: String, val Version: Double)
    class Adddevice_Speaker(val Name: String, val Brand: String, val Data_logging: Boolean, val History_tracking: Boolean, val Type: String, val Desc: String, val Version: Double)
    class Adddevice_TV(val Name: String, val Brand: String, val Data_logging: Boolean, val Ad_sense: Boolean, val History_tracking: Boolean, val Type: String, val Desc: String, val Version: Double)
    class Adddevice_Thermostat(val Name: String, val Brand: String, val Data_logging: Boolean, val Type: String, val Desc: String, val Version: Double)
}

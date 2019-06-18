package com.xp.xperiencurity.xperiencurity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

@Suppress("UNUSED_PARAMETER")
class Dashboard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
    }

    fun clickDashboardViewAlarm(view: View) {
        startActivity(Intent( this, DashboardViewAlarm::class.java))
    }

    fun clickDashboardViewCamera(view: View) {
        startActivity(Intent( this, DashboardViewCamera::class.java))
    }

    fun clickDashboardViewLight(view: View) {
        startActivity(Intent( this, DashboardViewLight::class.java))
    }

    fun clickDashboardViewSpeaker(view: View) {
        startActivity(Intent( this, DashboardViewSpeaker::class.java))
    }

    fun clickDashboardViewTV(view: View) {
        startActivity(Intent( this, DashboardViewTV::class.java))
    }

    fun clickDashboardViewThermostat(view: View) {
        startActivity(Intent( this, DashboardViewThermostat::class.java))
    }
}

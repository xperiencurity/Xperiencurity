package com.xp.xperiencurity.xperiencurity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fabric.with(this, Crashlytics())
        setContentView(R.layout.activity_main)
    }

    fun clickSupport(view: View) {
        startActivity(Intent( this, SupportPage::class.java))
    }

    fun clickDashboard(view: View) {
        startActivity(Intent(this, Dashboard::class.java))
    }

    fun clickDataFilter(view: View) {
        startActivity(Intent(this, DataFilter::class.java))
    }

    /*fun clickAddDevice(view: View) {
        startActivity(Intent(this, AddDevice::class.java))
    }

    fun clickRemoveDevice(view: View) {
        startActivity(Intent(this, RemoveDevice::class.java))
    }

    fun clickViewLogs(view: View) {
        startActivity(Intent(this, ViewLogs::class.java))
    }*/

    fun clickUpdates(view: View) {
        startActivity(Intent(this, CheckForUpdates::class.java))
    }

    /*fun clickSettings(view: View) {
        startActivity(Intent(this, Settings::class.java))
    }

    fun clickLogout(view: View) {
        startActivity(Intent(this, Logout::class.java))
    }*/
}
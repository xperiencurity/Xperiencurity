package com.xp.xperiencurity.xperiencurity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class LoginPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
    }

    fun clickMenu(view: View) {
        startActivity(Intent(this, MainActivity::class.java))
    }

    fun clickRegister(view: View) {
        startActivity(Intent(this, RegisterPage::class.java))
    }
}

package com.xp.xperiencurity.xperiencurity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login_page.*


class LoginPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        loginbtn.setOnClickListener{
            setLogin()
    }}



    fun clickRegister(view: View) {
        startActivity(Intent(this, RegisterPage::class.java))
    }

    private fun setLogin(){
        val email = loginEmail.text.toString()
        val password = loginpw.text.toString()

        Log.d("Login", "Attempt login with email/pw: $email/*****")
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please input email/password in the required field", Toast.LENGTH_SHORT).show()
            return
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if (!it.isSuccessful) return@addOnCompleteListener
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{
                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
            }
    }
    fun clickLogin(view: View) {
        startActivity(Intent(this, MainActivity::class.java))
    }

}

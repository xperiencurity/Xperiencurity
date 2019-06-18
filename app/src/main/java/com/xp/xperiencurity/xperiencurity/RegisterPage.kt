package com.xp.xperiencurity.xperiencurity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register_page.*

class RegisterPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)



        regbtn.setOnClickListener {
            setRegister()
        }
    }

    fun clickBack(view: View) {
        startActivity(Intent(this, LoginPage::class.java))
    }

    private fun setRegister() {
        val id = textid.text.toString()
        val email = textEmail.text.toString()
        val password = textpw.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please input email/password in the required field", Toast.LENGTH_SHORT).show()
            return
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener
                Log.d("Main", "User registered successful with uid: ${it.result?.user?.uid}")
                Toast.makeText(this, "Register Successful", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Log.d("Main", "Failed to create user: ${it.message}")
                Toast.makeText(this, "Register Failed", Toast.LENGTH_SHORT).show()
            }
    }
}

package com.xp.xperiencurity.xperiencurity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
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

        val email = textEmail.text.toString()
        val password = textpw.text.toString()
        val conpassword = textpw2.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please input email/password in the required field", Toast.LENGTH_SHORT).show()
            return
        }

        else if (!isValidEmail(email)){
            Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show()
        }

        else if (password.length < 6){
            Toast.makeText(this, "The minimum characters required for password is six", Toast.LENGTH_SHORT).show()
        }
        else if (!password.equals(conpassword)) {
            Toast.makeText(this, "Both password inputs must be identical", Toast.LENGTH_SHORT).show()
        }
        else if (password.equals(conpassword)){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener
                val intent = Intent(this, LoginPage::class.java)
                startActivity(intent)
                Log.d("Main", "User registered successful with uid: ${it.result?.user?.uid}")
                Toast.makeText(this, "Register Successful", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Log.d("Main", "Failed to create user: ${it.message}")
                Toast.makeText(this, "Register Failed, Email address already in used.", Toast.LENGTH_SHORT).show()
            }}
    }


    private fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}

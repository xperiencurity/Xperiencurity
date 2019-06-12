package com.xp.xperiencurity.xperiencurity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_feedback_form.emailAddress
import kotlinx.android.synthetic.main.activity_feedback_form.fullName
import kotlinx.android.synthetic.main.activity_feedback_form.subject
import kotlinx.android.synthetic.main.activity_feedback_form.yourMessage
import kotlinx.coroutines.*
import java.lang.Exception


class FeedbackForm : AppCompatActivity(), CoroutineScope by MainScope() {

    private lateinit var radioGroup: RadioGroup
    private lateinit var radioButton: RadioButton
    private lateinit var result: String
    private lateinit var fName: String
    private lateinit var eAddress: String
    private lateinit var subj: String
    private lateinit var message: String
    private var empty = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback_form)
    }

    fun submitFeedback (view: View) {
        launch {
            fetchUserInput()
            withContext(Dispatchers.Default) {
                if (isEmpty(fName) || isEmpty(eAddress) || isEmpty(subj) || isEmpty(message) || isEmpty(result))
                    empty = true
                if (empty) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@FeedbackForm, "There is one or more that are not filled in!", Toast.LENGTH_SHORT).show()
                    }
                }
                if (isValidEmail(eAddress)) {
                    submitUserInput()
                }
                else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@FeedbackForm, "Invalid email address", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun fetchUserInput() {
        try {
            fName = fullName.text.toString()
            eAddress = emailAddress.text.toString()
            subj = subject.text.toString()
            message = yourMessage.text.toString()
            radioGroup = findViewById(R.id.radGroupFollow)
            var radioId = radioGroup.checkedRadioButtonId
            radioButton = findViewById(radioId)
            result = radioButton.text as String
        } catch (e: Exception) {
            Toast.makeText(this, "There is one or more that are not filled in!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isEmpty(input: String):Boolean {
        return input.trim().isEmpty()
    }

    private fun submitUserInput() {
        val to = arrayOf("xperiencurity@gmail.com")
        val email = Intent(Intent.ACTION_SEND)

        email.putExtra(Intent.EXTRA_EMAIL, to)
        email.putExtra(Intent.EXTRA_SUBJECT, "Feedback: $subj")
        email.putExtra(
            Intent.EXTRA_TEXT, "Full Name: $fName\n" +
                    message +
                    "\nFrom: $eAddress" +
                    "\nFollow Up: $result"
        )

        email.type = "message/rfc822"
        startActivity(Intent.createChooser(email, "Choose app to send mail"))
    }

    private fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}

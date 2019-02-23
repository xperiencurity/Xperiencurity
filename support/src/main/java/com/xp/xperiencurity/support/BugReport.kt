package com.xp.xperiencurity.support

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_bug_report.*


class BugReport : AppCompatActivity() {

    private lateinit var radioGroup: RadioGroup
    private lateinit var radioButton: RadioButton
    private lateinit var result: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback_form)
    }

    fun submitFeedback (view: View) {
        try {
            var fName = fullName.text.toString()
            var eAddress = emailAddress.text.toString()
            var subj = subject.text.toString()
            var message = yourMessage.text.toString()

            val to = arrayOf("xperiencurity@gmail.com")

            radioGroup = findViewById(R.id.radGroupFollow)

            var radioId = radioGroup.checkedRadioButtonId
            radioButton = findViewById(radioId)
            result = radioButton.text as String

            var email = Intent(Intent.ACTION_SEND)

            email.putExtra(android.content.Intent.EXTRA_EMAIL, to)
            email.putExtra(Intent.EXTRA_SUBJECT, "Bug Report: $subj")
            email.putExtra(
                Intent.EXTRA_TEXT, "Full Name: $fName\n" +
                        message +
                        "\nFrom: $eAddress" +
                        "\nFollow Up: $result"
            )

            email.type = "message/rfc822"
            startActivity(Intent.createChooser(email, "Choose app to send mail"))
        } catch (e: Exception) {
            Toast.makeText(this, "There is one or more that are not filled in!", Toast.LENGTH_SHORT).show()
        }
    }
}

package com.xp.xperiencurity.xperiencurity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_bug_report.*
import android.util.Patterns
import android.text.TextUtils
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class BugReport : AppCompatActivity(), CoroutineScope by MainScope() {

    private lateinit var radioGroup: RadioGroup
    private lateinit var radioButton: RadioButton
    private var radioId = -1
    private lateinit var result: String
    private lateinit var fName: String
    private lateinit var eAddress: String
    private lateinit var subj: String
    private lateinit var message: String
    private var empty = false
    lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
        setContentView(R.layout.activity_bug_report)
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel() // all children coroutines gets destroyed automatically
    }

    fun submitBugReport(view: View) {
        launch {
            fetchUserInput()
            withContext(Dispatchers.Default) {
                empty = isEmpty(fName) || isEmpty(eAddress) || isEmpty(subj) || isEmpty(message) || !radSelected()
                if (empty) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@BugReport,
                            "There is one or more that are not filled in!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    if (isValidEmail(eAddress)) {
                        submitUserInput()
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@BugReport, "Invalid email address", Toast.LENGTH_SHORT).show()
                        }
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
            radioId = radioGroup.checkedRadioButtonId
            radioButton = findViewById(radioId)
            result = radioButton.text as String
        } catch (e: Exception) {
            Toast.makeText(this, "There is one or more that are not filled in!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun radSelected(): Boolean {
        return radioId != -1
    }

    private fun isEmpty(input: String): Boolean {
        return input.trim().isEmpty()
    }

    private fun submitUserInput() {

        val to = arrayOf("xperiencurity@gmail.com")
        val email = Intent(Intent.ACTION_SEND)

        email.putExtra(Intent.EXTRA_EMAIL, to)
        email.putExtra(Intent.EXTRA_SUBJECT, "Bug Report: $subj")
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

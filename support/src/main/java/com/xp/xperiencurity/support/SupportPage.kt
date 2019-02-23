package com.xp.xperiencurity.support

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class SupportPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support_page)
    }

    fun feedbackClicked(view: View) {
        startActivity(Intent(this, FeedbackForm::class.java))
    }

    fun bugReportClicked(view: View) {
        startActivity(Intent(this, BugReport::class.java))
    }
}

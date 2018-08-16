package com.eramiexample.firstkotlinapp.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.eramiexample.firstkotlinapp.R
 import kotlinx.android.synthetic.main.activity_login2.*

class LogINActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)
        email_sign_in_button.setOnClickListener {
            logIn()
        }

    }
    private fun logIn(){
        email.error= null
        password.error= null

        var textEmail = email.text.toString()
        var textPassword = password.text.toString()

        var cancel = false
        var focusView: View? = null

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(textPassword) && !isPasswordValid(textPassword)) {
            password.error = getString(R.string.error_invalid_password)
            focusView = password
            cancel = true
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(textEmail)) {
            email.error = getString(R.string.error_field_required)
            focusView = email
            cancel = true
        } else if (!isEmailValid(textEmail)) {
            email.error = getString(R.string.error_invalid_email)
            focusView = email
            cancel = true
        }
        if (cancel) {

            focusView?.requestFocus()
        } else {
            val intent = Intent(this@LogINActivity, NavActivity::class.java)
            startActivity(intent)
            finish()

        }


     }

    private fun isEmailValid(email: String): Boolean {
        //TODO: Replace this with your own logic
        return email.contains("@")
    }

    private fun isPasswordValid(password: String): Boolean {
        //TODO: Replace this with your own logic
        return password.length > 4
    }

}

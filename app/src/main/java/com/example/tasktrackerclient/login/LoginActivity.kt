package com.example.tasktrackerclient.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tasktrackerclient.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_login.*

class LoginActivity : AppCompatActivity() {

    val PREFERENCES = "myPreferences"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(toolbar)

        save_credentials.setOnClickListener {
            saveCredentials()
        }

        returnMain.setOnClickListener {
            finish()
        }

    }

    fun saveCredentials() {
        val sharedPref: SharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("username", username.text.toString())
        editor.putString("password", password.text.toString())
        editor.commit()
    }

}
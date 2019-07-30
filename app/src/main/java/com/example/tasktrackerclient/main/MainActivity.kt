package com.example.tasktrackerclient.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.tasktrackerclient.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    val PREFERENCES = "myPreferences"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        save_credentials.setOnClickListener {
            saveCredentials()
        }

        viewTaskInstances.setOnClickListener {
            var intent = Intent("android.intent.action.ViewTaskInstances")
            startActivity(intent)
        }

        createTask.setOnClickListener {
            var intent = Intent("android.intent.action.CreateTask")
            startActivity(intent)
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

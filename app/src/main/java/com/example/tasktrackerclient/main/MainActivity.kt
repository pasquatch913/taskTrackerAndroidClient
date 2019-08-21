package com.example.tasktrackerclient.main

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.tasktrackerclient.R
import com.example.tasktrackerclient.database.DbHelper
import com.example.tasktrackerclient.database.DbHelper.Companion.COLUMN_ID
import com.example.tasktrackerclient.database.DbHelper.Companion.COLUMN_NAME
import com.example.tasktrackerclient.database.DbHelper.Companion.TABLE_NAME
import com.example.tasktrackerclient.database.TaskEntity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        loginButton.setOnClickListener {
            var intent = Intent("android.intent.action.LoginActivity")
            startActivity(intent)
        }

        viewTaskInstances.setOnClickListener {
            var intent = Intent("android.intent.action.ViewTaskInstancesActivity")
            startActivity(intent)
        }

        createTask.setOnClickListener {
            var intent = Intent("android.intent.action.CreateTaskActivity")
            startActivity(intent)
        }
    }

}

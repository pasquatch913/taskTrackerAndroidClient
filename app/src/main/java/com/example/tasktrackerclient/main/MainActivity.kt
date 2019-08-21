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

    var tasklist = listOf<TaskEntity>(TaskEntity(1, "first task"), TaskEntity(2, "other task"),
        TaskEntity(3, "and another??")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val uri = Uri.parse("content://${applicationInfo.packageName}.provider/$TABLE_NAME")
        tasklist.forEach {
            val values = ContentValues()
            values.put(COLUMN_ID, it.id)
            values.put(COLUMN_NAME, it.name)
            Log.d("inserter", "task ID ${it.id} and name ${it.name} inserted")
            contentResolver.insert(uri, values)
        }

        val mCursor = contentResolver.query(uri,
            null,
            null,
            null,
            "$COLUMN_ID DESC")

        if (mCursor?.moveToFirst() as Boolean) {
            (0..mCursor.count).forEach {
                Log.d("reader",
                    "reading id ${mCursor?.getInt(mCursor.getColumnIndex(COLUMN_ID))}" +
                            " and name ${mCursor?.getString(mCursor.getColumnIndex(COLUMN_NAME))}")
                mCursor.moveToNext()
            }

        }


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

package com.example.tasktrackerclient.createTask

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.tasktrackerclient.R
import com.example.tasktrackerclient.SubscriptionRequest
import com.example.tasktrackerclient.TaskPeriod
import com.example.tasktrackerclient.rest.TaskTrackerService
import kotlinx.android.synthetic.main.activity_show_instances.*
import kotlinx.android.synthetic.main.content_create_task.*
import kotlinx.android.synthetic.main.content_show_instances.returnMain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CreateTask : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)
        setSupportActionBar(toolbar)

        createSubscription.setOnClickListener {
            createSubscriptionClickListener(this)
        }

        returnMain.setOnClickListener {
            finish()
        }
    }

    private fun createSubscriptionClickListener(context: Context) {
        val service = TaskTrackerService(context)

        val period = when (taskPeriod.checkedRadioButtonId) {
            daily.id -> TaskPeriod.DAILY
            weekly.id -> TaskPeriod.WEEKLY
            monthly.id -> TaskPeriod.MONTHLY
            else -> throw RuntimeException("Oops! No Matching task period.")
        }

        val request = SubscriptionRequest(
            taskName.text.toString(),
            goal.text.toString().toInt(),
            weight.text.toString().toInt(),
            period
        )

        GlobalScope.launch(Dispatchers.Main) {
            val response = service.newTaskSubscription(request).await()
            println(response)
            if (response.isSuccessful) {
                println(response.code())
                finish()
            } else {
                println("failed to execute request")
                println("call: " + response)
            }
        }
    }

}
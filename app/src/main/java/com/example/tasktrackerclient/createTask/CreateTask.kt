package com.example.tasktrackerclient.createTask

import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.DatePicker
import android.widget.RadioGroup
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
import java.text.SimpleDateFormat
import java.util.*

class CreateTask : AppCompatActivity() {

    var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)
        setSupportActionBar(toolbar)

        oneTimeTaskDueDate.setText("--/--/----")

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        oneTimeTaskDueDate!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(
                    this@CreateTask,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

        })

        taskType.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group: RadioGroup?, checkedId: Int ->
                showTaskTypeOptions(group, checkedId)
            })

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

    private fun showTaskTypeOptions(group: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            taskSubscription.id -> {
                oneTimeTaskDueDate.visibility = View.GONE
                taskPeriod.visibility = View.VISIBLE
            }
            oneTimeTask.id -> {
                taskPeriod.visibility = View.GONE
                oneTimeTaskDueDate.visibility = View.VISIBLE
            }
            else -> {
                taskPeriod.visibility = View.GONE
                oneTimeTaskDueDate.visibility = View.GONE
            }
        }
    }

    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        oneTimeTaskDueDate!!.setText(sdf.format(cal.time))
    }

}
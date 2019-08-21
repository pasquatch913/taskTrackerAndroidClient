package com.example.tasktrackerclient.createTask

import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.tasktrackerclient.OneTimeTaskRequest
import com.example.tasktrackerclient.R
import com.example.tasktrackerclient.SubscriptionRequest
import com.example.tasktrackerclient.TaskPeriod
import com.example.tasktrackerclient.rest.RestClient
import kotlinx.android.synthetic.main.activity_show_instances.*
import kotlinx.android.synthetic.main.content_create_task.*
import kotlinx.android.synthetic.main.content_show_instances.returnMain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class CreateTaskActivity : AppCompatActivity() {

    var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)
        setSupportActionBar(toolbar)

        taskDueDate.setText("--/--/----")
        taskDueDate!!.setOnFocusChangeListener(showCalendarFocusListener)
        taskDueDate!!.setOnClickListener(showCalendarClickListener)

        taskType.setOnCheckedChangeListener { group: RadioGroup?, checkedId: Int ->
            showTaskTypeOptions(group, checkedId)
        }

        returnMain.setOnClickListener {
            finish()
        }
    }

    private fun createSubscriptionClickListener(context: Context) {
        val service = RestClient(context)

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

    private fun createOneTimeTaskClickListener(context: Context) {
        val service = RestClient(context)

        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        formatter =
            formatter.withLocale(Locale.US)  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
        val date = LocalDate.parse(taskDueDate.text, formatter)

        val request = OneTimeTaskRequest(
            taskName.text.toString(),
            weight.text.toString().toInt(),
            goal.text.toString().toInt(),
            0,
            date,
            true
        )

        GlobalScope.launch(Dispatchers.Main) {
            val response = service.newOneTimeTask(request).await()
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
                createButton.setEnabled(true)
                createButton.setOnClickListener {
                    createSubscriptionClickListener(this)
                }
                taskDueDate.visibility = View.GONE
                taskPeriod.visibility = View.VISIBLE
            }
            oneTimeTask.id -> {
                createButton.setEnabled(true)
                createButton.setOnClickListener {
                    createOneTimeTaskClickListener(this)
                }
                taskPeriod.visibility = View.GONE
                taskDueDate.visibility = View.VISIBLE
            }
            else -> {
                createButton.setEnabled(false)
                taskPeriod.visibility = View.GONE
                taskDueDate.visibility = View.GONE
            }
        }
    }

    private fun updateDateInView() {
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        taskDueDate!!.setText(sdf.format(cal.time))
    }

    private val showCalendarFocusListener = object : View.OnFocusChangeListener {
        override fun onFocusChange(view: View, gainFocus: Boolean) {
            if (gainFocus) {
                showCalendar()
            }
        }
    }

    private val showCalendarClickListener = object : View.OnClickListener {
        override fun onClick(view: View) {
            showCalendar()
        }
    }

    private val showCalendar = {
        DatePickerDialog(
            this@CreateTaskActivity,
            dateSetListener,
            // set DatePickerDialog to point to today's date when it loads up
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private val dateSetListener = object : DatePickerDialog.OnDateSetListener {
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

}
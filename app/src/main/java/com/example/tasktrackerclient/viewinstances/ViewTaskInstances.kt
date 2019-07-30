package com.example.tasktrackerclient.viewinstances

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.tasktrackerclient.R
import com.example.tasktrackerclient.rest.TaskTrackerService

import kotlinx.android.synthetic.main.activity_show_instances.*
import kotlinx.android.synthetic.main.content_show_instances.*
import kotlinx.android.synthetic.main.task_instance_row.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ViewTaskInstances : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_instances)
        setSupportActionBar(toolbar)

        recyclerView_task_instance_rows.layoutManager = LinearLayoutManager(this)

        fetchAllTasks(this)

        returnMain.setOnClickListener {
            finish()
        }
    }


    fun fetchAllTasks(context: Context) {
        val service = TaskTrackerService(context)
        GlobalScope.launch(Dispatchers.Main) {
            val response = service.fetchAllActiveTasks().await()
            runOnUiThread {
                recyclerView_task_instance_rows.adapter =
                    ViewTaskInstancesAdapter(response.body()!!, context, { view: View -> clickListener(view, context) })
            }
        }
    }

    private fun clickListener(view: View, context: Context) {
        val data = view
        val currentCompletions = data.taskCompletions.text
        val newCompletions = (currentCompletions as String).toInt() + 1
        val service = TaskTrackerService(context)

        GlobalScope.launch(Dispatchers.Main) {
            val response = service.incrementTaskCompletions(data.taskId.text.toString().toInt(), newCompletions).await()
            println(response)
            if (response.isSuccessful) {
                println(response.code())
                if (response.code() == 202) {
                    runOnUiThread {
                        view.taskCompletions.text = newCompletions.toString()
                    }
                }
            }
            else {
                println("failed to execute request")
                println("call: " + response)
            }
        }
    }

}

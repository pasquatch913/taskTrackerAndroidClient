package com.example.tasktrackerclient.viewinstances

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.tasktrackerclient.R
import com.example.tasktrackerclient.TaskDTO
import com.example.tasktrackerclient.rest.TaskTrackerService
import kotlinx.android.synthetic.main.activity_show_instances.*
import kotlinx.android.synthetic.main.content_show_instances.*
import kotlinx.android.synthetic.main.task_instance_row.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ViewTaskInstancesActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private var taskList: List<TaskDTO> = emptyList()
    private var adapter: ViewTaskInstancesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_instances)
        setSupportActionBar(toolbar)
        recyclerView = findViewById(R.id.recyclerView_task_instance_rows)

        fetchAllTasks(this)

        recyclerView_task_instance_rows.layoutManager = LinearLayoutManager(applicationContext)

        recyclerView!!.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        adapter = ViewTaskInstancesAdapter(taskList, this,
            { view: View -> incrementClickListener(view, this) },
            { view: View -> decrementClickListener(view, this) },
            { view: View -> deactivateTask(view, this) })

        recyclerView!!.adapter = adapter

        returnMain.setOnClickListener {
            finish()
        }
    }


    fun fetchAllTasks(context: Context) {
        GlobalScope.launch(Dispatchers.Main) {
            val restService = TaskTrackerService(context)
            val response = restService.fetchAllActiveTasks().await()
            adapter?.data = response.body()!!
            adapter?.notifyDataSetChanged()
        }
    }

    private fun incrementClickListener(view: View, context: Context) {
        val data = view
        val currentCompletions = data.taskCompletions.text
        val newCompletions = (currentCompletions as String).toInt() + 1
        val restService = TaskTrackerService(context)

        GlobalScope.launch(Dispatchers.Main) {
            val response =
                restService.updateTaskCompletions(data.taskId.text.toString().toInt(), newCompletions).await()
            println(response)
            if (response.isSuccessful) {
                println(response.code())
                if (response.code() == 202) {
                    runOnUiThread {
                        view.taskCompletions.text = newCompletions.toString()
                    }
                }
            } else {
                println("failed to execute request")
                println("call: " + response)
            }
        }
    }

    private fun decrementClickListener(view: View, context: Context) {
        val data = view
        val currentCompletions = data.taskCompletions.text
        val newCompletions = (currentCompletions as String).toInt() - 1
        val restService = TaskTrackerService(context)

        GlobalScope.launch(Dispatchers.Main) {
            val response =
                restService.updateTaskCompletions(data.taskId.text.toString().toInt(), newCompletions).await()
            println(response)
            if (response.isSuccessful) {
                println(response.code())
                if (response.code() == 202) {
                    runOnUiThread {
                        view.taskCompletions.text = newCompletions.toString()
                    }
                }
            } else {
                println("failed to execute request")
                println("call: " + response)
            }
        }
    }

    private fun deactivateTask(view: View, context: Context) {
        val data = view
        val restService = TaskTrackerService(context)

        GlobalScope.launch(Dispatchers.Main) {
            val response = restService.unsubscribeFromTask(data.taskId.text.toString().toInt()).await()
            println(response)
            if (response.isSuccessful) {
                println(response.code())
                if (response.code() == 202) {
                    runOnUiThread {
                        view.visibility = View.GONE
                    }
                }
            } else {
                println("failed to execute request")
                println("call: " + response)
            }
        }
    }

}

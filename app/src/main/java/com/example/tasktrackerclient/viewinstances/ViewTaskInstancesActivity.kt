package com.example.tasktrackerclient.viewinstances

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.tasktrackerclient.R
import com.example.tasktrackerclient.TaskDTO
import com.example.tasktrackerclient.database.DbHelper
import com.example.tasktrackerclient.database.TaskMapper
import com.example.tasktrackerclient.database.TaskProvider_Room
import com.example.tasktrackerclient.rest.TaskTrackerService
import kotlinx.android.synthetic.main.content_show_instances.*
import kotlinx.android.synthetic.main.task_instance_row.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ViewTaskInstancesActivity : AppCompatActivity(), CoroutineScope {

    private var recyclerView: androidx.recyclerview.widget.RecyclerView? = null
    private var taskList: List<TaskDTO> = emptyList()
    private var adapter: ViewTaskInstancesAdapter? = null
    private var job: Job = Job()
    private val mapper = TaskMapper()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_instances)
        recyclerView = findViewById(R.id.recyclerView_task_instance_rows)

        fetchAllTasks(this)

        recyclerView_task_instance_rows.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(applicationContext)

        recyclerView!!.addItemDecoration(
            androidx.recyclerview.widget.DividerItemDecoration(
                this,
                androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
            )
        )

        adapter = ViewTaskInstancesAdapter(taskList, this,
            { view: View -> incrementClickListener(view, this) },
            { view: View -> decrementClickListener(view, this) },
            { view: View -> deactivateTask(view, this) })

        recyclerView!!.adapter = adapter

        returnMain.setOnClickListener {
            finish()
        }
    }


    private fun fetchAllTasks(context: Context) {
        launch {
            val restService = TaskTrackerService(context)
            val response = restService.fetchAllActiveTasks().await()
            val responseList = ArrayList<TaskDTO>()
            response.body()!!.map(mapper::taskInstanceResponseToTaskDto)
                .map(responseList::add)
            adapter?.data = responseList
            adapter?.notifyDataSetChanged()

            val uri = Uri.parse("content://${applicationInfo.packageName}.provider/${DbHelper.TABLE_NAME}")
            responseList.map(mapper::taskDtoToTaskEntity)
                .map(mapper::taskEntityToContentValues)
                .map { n -> contentResolver.insert(uri, n) }
        }
    }

    private fun incrementClickListener(view: View, context: Context) {
        val data = view
        val currentCompletions = data.taskCompletions.text
        val newCompletions = (currentCompletions as String).toInt() + 1
        val restService = TaskTrackerService(context)

        launch {
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

        launch {
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
        val rowId = data.taskId.text.toString().toInt()

        launch {
            val response = restService.unsubscribeFromTask(rowId).await()
            println(response)
            if (response.isSuccessful) {
                adapter?.removeTaskFromView(rowId)
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

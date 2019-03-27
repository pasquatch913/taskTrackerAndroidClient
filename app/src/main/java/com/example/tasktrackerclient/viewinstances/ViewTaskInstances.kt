package com.example.tasktrackerclient.viewinstances

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.tasktrackerclient.LocalDateAdapter
import com.example.tasktrackerclient.OneTimeTaskEntity
import com.example.tasktrackerclient.R
import com.example.tasktrackerclient.rest.RequestBuilder
import com.google.gson.GsonBuilder

import kotlinx.android.synthetic.main.activity_show_instances.*
import kotlinx.android.synthetic.main.content_show_instances.*
import kotlinx.android.synthetic.main.task_instance_row.view.*
import okhttp3.*
import java.io.IOException
import java.io.StringReader
import java.time.LocalDate

class ViewTaskInstances : AppCompatActivity() {

    val requestBuilder = RequestBuilder(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_instances)
        setSupportActionBar(toolbar)

        recyclerView_second.layoutManager = LinearLayoutManager(this)

        fetchOneTimeTasks(this)

        returnMain.setOnClickListener {
            finish()
        }
    }

    fun fetchOneTimeTasks(context: Context) {
        val request = requestBuilder.getOneTimeTasksRequest()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("failed to execute request")
                println("call: " + call)
                println("exception" + e)
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response?.body()?.string() ?: ""
                var stringReader: StringReader = StringReader(body)
                println(body)

                val gson = GsonBuilder()
                    .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter()).create()

                val taskList = gson.fromJson(stringReader, Array<OneTimeTaskEntity>::class.java).toList()

                runOnUiThread {
                    recyclerView_second.adapter =
                        ViewTaskInstancesAdapter(taskList, context, { view : View -> clickListener(view) })
                }
            }
        })
    }

    private fun clickListener(view: View) {
        val data = view
        val currentCompletions = data.taskCompletions.text
        val newCompletions = (currentCompletions as String).toInt() + 1
        val request = requestBuilder.incrementOneTimeTaskRequest(data.taskId.text.toString(), newCompletions)
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("failed to execute request")
                println("call: " + call)
                println("exception" + e)
            }

            override fun onResponse(call: Call, response: Response) {
                println(response.code())
                if (response.code() == 202) {
                    runOnUiThread {
                        view.taskCompletions.text = newCompletions.toString()
                    }

                }
            }
        })
    }

}

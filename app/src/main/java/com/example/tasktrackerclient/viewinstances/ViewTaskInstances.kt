package com.example.tasktrackerclient.viewinstances

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import com.example.tasktrackerclient.LocalDateAdapter
import com.example.tasktrackerclient.OneTimeTaskEntity
import com.example.tasktrackerclient.R
import com.google.gson.GsonBuilder

import kotlinx.android.synthetic.main.activity_show_instances.*
import kotlinx.android.synthetic.main.content_show_instances.*
import okhttp3.*
import java.io.IOException
import java.io.StringReader
import java.lang.Exception
import java.time.LocalDate

class ViewTaskInstances : AppCompatActivity() {

    val PREFERENCES = "myPreferences"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_instances)
        setSupportActionBar(toolbar)

        recyclerView_second.layoutManager = LinearLayoutManager(this)

        fetchOneTimeTasks()

        returnMain.setOnClickListener {
            finish()
        }
    }

    fun fetchOneTimeTasks() {
        val url = "http://pasquatch.com/api/oneTimeTasks"
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", getCredentials())
            .build()
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
                        ViewTaskInstancesAdapter(taskList)
                }
            }
        })
    }

    fun getCredentials(): String {
        val sharedPref = this.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE) ?: throw Exception()
        val username = sharedPref.getString("username", "")
        val password = sharedPref.getString("password", "")
        return Credentials.basic(username, password)
    }
}

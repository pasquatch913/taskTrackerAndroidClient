package com.example.tasktrackerclient.rest

import android.content.Context
import com.example.tasktrackerclient.LocalDateAdapter
import com.example.tasktrackerclient.OneTimeTaskEntity
import com.example.tasktrackerclient.viewinstances.ViewTaskInstancesAdapter
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.content_show_instances.*
import okhttp3.*
import java.io.IOException
import java.io.StringReader
import java.lang.Exception
import java.time.LocalDate

class RequestBuilder(context: Context) {

    val baseUrl = "http://pasquatch.com/api"

    val context = context
    val PREFERENCES = "myPreferences"

    fun getCredentials(): String {
        val sharedPref = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE) ?: throw Exception()
        val username = sharedPref.getString("username", "")
        val password = sharedPref.getString("password", "")
        return Credentials.basic(username, password)
    }

    fun getOneTimeTasksRequest(): Request {
        val url = baseUrl + "/oneTimeTasks"
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", getCredentials())
            .build()
        return request
    }

    fun incrementOneTimeTaskRequest(id: String, value: Int): Request {
        val url = "$baseUrl/tasks/oneTime/$id/completions/value/$value"
        val requestBody = RequestBody.create(null, "")
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", getCredentials())
            .method("POST", requestBody)
            .build()
        return request
    }

}
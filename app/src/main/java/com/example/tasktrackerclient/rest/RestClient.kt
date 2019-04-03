package com.example.tasktrackerclient.rest

import android.content.Context
import com.example.tasktrackerclient.LocalDateAdapter
import com.example.tasktrackerclient.OneTimeTaskEntity
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Call
import okhttp3.Credentials
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.StringReader
import java.lang.Exception
import java.time.LocalDate

class RestClient(context: Context) {

    val baseUrl = "http://pasquatch.com/api/"

    val context = context
    val PREFERENCES = "myPreferences"

    val gson = GsonBuilder()
        .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
        .create()

    fun getCredentials(): String {
        val sharedPref = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE) ?: throw Exception()
        val username = sharedPref.getString("username", "")
        val password = sharedPref.getString("password", "")
        return Credentials.basic(username, password)
    }

    val client = OkHttpClient().newBuilder()
        .addInterceptor(AuthorizationInterceptor(getCredentials()))
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val taskTrackerService = retrofit.create(TaskTrackerService::class.java)


}
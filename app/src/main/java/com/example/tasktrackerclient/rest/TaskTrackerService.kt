package com.example.tasktrackerclient.rest



import android.content.Context
import com.example.tasktrackerclient.LocalDateAdapter
import com.example.tasktrackerclient.OneTimeTaskEntity
import com.example.tasktrackerclient.TaskDTO
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Credentials
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.time.LocalDate

interface TaskTrackerService {

    @GET("oneTimeTasks")
    fun fetchOneTimeTasks() : Deferred<Response<List<OneTimeTaskEntity>>>

    @GET("tasks")
    fun fetchAllTasks(): Deferred<Response<List<TaskDTO>>>

    @POST("tasks/oneTime/{id}/completions/{value}")
    fun incrementOneTimeTask(@Path("id") id: Int, @Path("value") value: Int) : Deferred<Response<Void>>

    companion object {
        operator fun invoke(context: Context): TaskTrackerService {
            val baseUrl = "https://pasquatch.com/api/"

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

            val okHttpClient = OkHttpClient().newBuilder()
                .addInterceptor(AuthorizationInterceptor(getCredentials()))
                .build()

           return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build().create(TaskTrackerService::class.java)
        }
    }
}
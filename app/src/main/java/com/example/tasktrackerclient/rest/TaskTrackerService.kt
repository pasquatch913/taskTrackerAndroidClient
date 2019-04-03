package com.example.tasktrackerclient.rest



import com.example.tasktrackerclient.OneTimeTaskEntity
import com.example.tasktrackerclient.viewinstances.TaskInstanceEntity
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TaskTrackerService {

    @GET("oneTimeTasks")
    fun fetchOneTimeTasks() : Deferred<Response<List<OneTimeTaskEntity>>>

    @POST("tasks/oneTime/{id}/completions/value/{value}")
    fun incrementOneTimeTask(@Path("id") id: Int, @Path("value") value: Int) : Deferred<Response<Void>>
//
//    @GET("taskInstances")
//    fun fetchTaskInstances() : Call<TaskInstanceEntity>
}
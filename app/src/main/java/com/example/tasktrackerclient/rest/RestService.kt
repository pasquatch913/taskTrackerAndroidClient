package com.example.tasktrackerclient.rest

import android.appwidget.AppWidgetManager
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.tasktrackerclient.R
import com.example.tasktrackerclient.TaskDTO
import com.example.tasktrackerclient.database.DbHelper
import com.example.tasktrackerclient.database.TaskMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import kotlin.coroutines.CoroutineContext

class RestService(val context: Context) : CoroutineScope {

    val mapper = TaskMapper()

    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    val webRequestService = RestClient(context)

    val uri = Uri.parse("content://com.example.tasktrackerclient.provider/${DbHelper.TABLE_NAME}")

    // success should be handled with callback to allow more wide usage
    fun updateTaskCompletions(
        taskId: Int,
        newCompletions: Int,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        launch {
            try {
                val response =
                    webRequestService.updateTaskCompletions(taskId, newCompletions).await()
                println(response)
                if (response.isSuccessful) {
                    println(response.code())
                    val whereClause = "${DbHelper.COLUMN_ID} = ?"
                    val whereArgs = arrayOf(taskId.toString())
                    val newValue = ContentValues()
                    newValue.put(DbHelper.COLUMN_COMPS, newCompletions)
                    context.contentResolver.update(uri, newValue, whereClause, whereArgs)
                    //notify that dataset is changed
                    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widgetCollectionList)
                } else {
                    println("failed to execute request")
                    println("call: " + response)
                }
            } catch (ex: SocketTimeoutException) {
                Log.d("RestService", "request to update remote server timed out after 10s!")
            }
        }
    }

    fun updateAllTasks(appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        launch {
            try {
                val response =
                    webRequestService.fetchAllActiveTasks().await()
                println(response)
                if (response.isSuccessful) {
                    println(response.code())
                    val deleted = context.contentResolver.delete(uri, null, null)
                    Log.d("Rows deleted", deleted.toString())
                    val responseList = ArrayList<TaskDTO>()
                    response.body()!!.map(mapper::taskInstanceResponseToTaskDto)
                        .map(responseList::add)

                    responseList.map(mapper::taskDtoToTaskEntity)
                        .map(mapper::taskEntityToContentValues)
                        .map { n -> context.contentResolver.insert(uri, n) }
                    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widgetCollectionList)
                } else {
                    println("failed to execute request")
                    println("call: " + response)
                }
            } catch (ex: SocketTimeoutException) {
                Log.d("RestService", "request to pull from remote server timed out after 10s!")
            }
        }
    }
}
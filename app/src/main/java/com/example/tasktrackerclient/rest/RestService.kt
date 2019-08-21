package com.example.tasktrackerclient.rest

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import com.example.tasktrackerclient.database.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RestService (val context: Context) : CoroutineScope {

    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    val webRequestService = RestClient(context)

    val uri = Uri.parse("content://com.example.tasktrackerclient.provider/${DbHelper.TABLE_NAME}")

    fun updateTaskCompletions(taskId: Int, newCompletions: Int) {
        launch {
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
            } else {
                println("failed to execute request")
                println("call: " + response)
            }
        }
    }
}
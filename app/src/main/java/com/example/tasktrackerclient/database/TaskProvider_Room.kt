package com.example.tasktrackerclient.database

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import androidx.room.Room

class TaskProvider_Room : ContentProvider() {

    val mapper = TaskMapper()

    companion object {
        val PROVIDER_NAME = "com.pasquatch.TaskTrackerAndroidClient.TaskProvider_Room"
        val URL = "content://$PROVIDER_NAME/task_table"
        val CONTENT_URI = Uri.parse(URL)
        val MATCHER: UriMatcher? = null
    }

    init {
        val MATCHER = UriMatcher(UriMatcher.NO_MATCH)
        MATCHER.addURI(PROVIDER_NAME, "task_table", 1)
        MATCHER.addURI(PROVIDER_NAME, "task_table" + "/*", 2)
    }

    private lateinit var taskDatabase: TaskDatabase
    private var taskDao: TaskDatabaseDao? = null

    override fun onCreate(): Boolean {
        taskDatabase = Room.databaseBuilder(context, TaskDatabase::class.java, "task-cache").build()
        taskDao = taskDatabase.taskDao

        return true
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
//        val code = MATCHER?.match(uri)
        val cursor = taskDao?.selectAll()
        cursor?.setNotificationUri(context!!.contentResolver, uri)
        return cursor
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getType(uri: Uri): String? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun bulkInsert(uri: Uri, valuesArray: Array<ContentValues>): Int {
//        val database = taskDatabase
//        val tasks = emptyList<TaskEntity>().toMutableList()
//        for (i in valuesArray.indices) {
//            tasks.add(mapper.contentValuesToTaskEntity(valuesArray[i]))
//        }
//        return database.taskDao.insertAll(tasks).size
        return 1
    }

}
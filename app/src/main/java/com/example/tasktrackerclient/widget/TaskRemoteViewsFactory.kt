package com.example.tasktrackerclient.widget

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.example.tasktrackerclient.R
import com.example.tasktrackerclient.database.DbHelper
import com.example.tasktrackerclient.database.DbHelper.Companion.TABLE_NAME
import com.example.tasktrackerclient.database.TaskEntity
import com.example.tasktrackerclient.database.TaskMapper

class TaskRemoteViewsFactory(val context: Context) : RemoteViewsService.RemoteViewsFactory {

    val uri: Uri
    val array = ArrayList<TaskEntity>()
    val mapper = TaskMapper()

    init {
        uri = Uri.parse("content://${context.applicationInfo?.packageName}.provider/$TABLE_NAME")
    }

    override fun getLoadingView(): RemoteViews? = RemoteViews(context.packageName, R.layout.widget_row)

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(context.packageName, R.layout.widget_row)

        if (array.isEmpty()) populateCursor()

        rv.setTextViewText(R.id.widgetTaskName, array[position].name)
        rv.setTextViewText(R.id.widgetTaskCompletions, array[position].completions.toString())
        rv.setTextViewText(R.id.widgetTaskCompletionsGoal, array[position].completionsGoal.toString())
        rv.setTextViewText(R.id.widgetTaskDueDate, array[position].dueDate.toString())
        return rv
    }

    override fun getViewTypeCount() = 1

    override fun onCreate() {
        populateCursor()
    }

    override fun getItemId(position: Int) = position.toLong()

    override fun onDataSetChanged() {
        populateCursor()
    }

    override fun hasStableIds() = true

    override fun getCount() = array.size

    override fun onDestroy() {
        array.clear()
    }

    fun populateCursor() {
        array.clear()
        val mCursor = context.contentResolver.query(uri,
            null,
            null,
            null,
            "${DbHelper.COLUMN_ID} DESC")

        if (mCursor?.moveToFirst() as Boolean) {
            (0 until mCursor.count).forEach {
                array.add(mapper.cursorToTaskEntity(mCursor))
                Log.d("reader",
                    "reading id ${mCursor?.getInt(mCursor.getColumnIndex(DbHelper.COLUMN_ID))}" +
                            " and name ${mCursor?.getString(mCursor.getColumnIndex(DbHelper.COLUMN_NAME))}")
                mCursor.moveToNext()
            }

        }
        mCursor.close()
    }
}
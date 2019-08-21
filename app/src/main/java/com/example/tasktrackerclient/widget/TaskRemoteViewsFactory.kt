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

class TaskRemoteViewsFactory(val context: Context) : RemoteViewsService.RemoteViewsFactory {

    val uri: Uri
    val array = ArrayList<TaskEntity>()

    init {
        uri = Uri.parse("content://${context.applicationInfo?.packageName}.provider/$TABLE_NAME")
    }

    override fun getLoadingView(): RemoteViews? = RemoteViews(context.packageName, R.layout.widget_row)

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(context.packageName, R.layout.widget_row)

        if (array.isEmpty()) populateCursor()

        rv.setTextViewText(R.id.widgetTaskName, array[position].name)
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
                array.add(TaskEntity(
                    mCursor?.getInt(mCursor.getColumnIndex(DbHelper.COLUMN_ID)),
                    mCursor?.getString(mCursor.getColumnIndex(DbHelper.COLUMN_NAME))
                ))
                Log.d("reader",
                    "reading id ${mCursor?.getInt(mCursor.getColumnIndex(DbHelper.COLUMN_ID))}" +
                            " and name ${mCursor?.getString(mCursor.getColumnIndex(DbHelper.COLUMN_NAME))}")
                mCursor.moveToNext()
            }

        }
        mCursor.close()
    }
}
package com.example.tasktrackerclient.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.example.tasktrackerclient.R
import com.example.tasktrackerclient.TaskDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.text.DateFormat
import java.util.*
import kotlin.coroutines.CoroutineContext


class NewAppWidget : AppWidgetProvider(), CoroutineScope {

    private var job: Job = Job()

    private var collection: MutableList<TaskDTO> = mutableListOf()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    /**
     * Update a single app widget.  This is a helper method for the standard
     * onUpdate() callback that handles one widget update at a time.
     *
     * @param context          The application context.
     * @param appWidgetManager The app widget manager.
     * @param appWidgetId      The current app widget id.
     */
    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {

        // Get the count from prefs.
        val prefs = context.getSharedPreferences(SHARED_PREF_FILE, 0)
        var count = prefs.getInt(COUNT_KEY + appWidgetId, 0)
        count++

        // Get the current time.
        val dateString = DateFormat.getTimeInstance(DateFormat.SHORT).format(Date())

        // Construct the RemoteViews object.
        val views = RemoteViews(
            context.getPackageName(),
            R.layout.new_app_widget
        )
        views.setTextViewText(
            R.id.appwidget_id,
//            appWidgetId.toString()
            collection[0].name
        )
        views.setTextViewText(
            R.id.appwidget_update,
            context.getResources().getString(
                R.string.date_count_format, count, dateString
            )
        )

        // Save count back to prefs.
        val prefEditor = prefs.edit()
        prefEditor.putInt(COUNT_KEY + appWidgetId, count)
        prefEditor.apply()

        // Setup update button to send an update request as a pending intent.
        val intentUpdate = Intent(context, NewAppWidget::class.java)

        // The intent action must be an app widget update.
        intentUpdate.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE

        // Include the widget ID to be updated as an intent extra.
        val idArray = intArrayOf(appWidgetId)
        intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray)

        // Wrap it all in a pending intent to send a broadcast.
        // Use the app widget ID as the request code (third argument) so that
        // each intent is unique.
        val pendingUpdate = PendingIntent.getBroadcast(
            context,
            appWidgetId, intentUpdate, PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Assign the pending intent to the button onClick handler
        views.setOnClickPendingIntent(R.id.button_update, pendingUpdate)

        // Instruct the widget manager to update the widget.
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    /**
     * Override for onUpdate() method, to handle all widget update requests.
     *
     * @param context          The application context.
     * @param appWidgetManager The app widget manager.
     * @param appWidgetIds     An array of the app widget IDs.
     */
    override fun onUpdate(
        context: Context, appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {

        // There may be multiple widgets active, so update all of them.
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    companion object {

        // Name of shared preferences file & key
        private val SHARED_PREF_FILE = "com.example.android.appwidgetsample"
        private val COUNT_KEY = "count"
    }
}
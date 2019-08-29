package com.example.tasktrackerclient.widget

import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.example.tasktrackerclient.R
import com.example.tasktrackerclient.rest.RestService
import com.example.tasktrackerclient.viewinstances.ViewTaskInstancesActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class TaskWidgetProvider : AppWidgetProvider(), CoroutineScope {

    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    companion object {
        val ACTION_CLICK = "com.example.tasktrackerclient.widget.click"
        val PERIODIC_REFRESH = "com.example.tasktrackerclient.widget.refresh"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        Log.d("receiver", "intent received")

        val name = ComponentName(context, TaskWidgetProvider::class.java)
        val appWidgetIds: IntArray = AppWidgetManager.getInstance(context).getAppWidgetIds(name)
        if (appWidgetIds.isEmpty()) return

        var appWidgetManager = AppWidgetManager.getInstance(context)

        if (intent?.action == ACTION_CLICK) {
            val clickType = intent.getIntExtra("CLICK_TYPE", 0)
            // generic click means open the view task instances activity
            if (clickType == 0) {
                handleOpenActivityClick(context)
            }
            // don't ever try to update a task if there was a problem getting data for it
            if (clickType == 1
                && intent.getIntExtra("CLICKED_ID", -1) != -1
                && intent.getIntExtra("CLICKED_COMPLETIONS", -999) != -999
            ) {
                handleIncrementCompletionsClick(context, intent, appWidgetManager, appWidgetIds)
            }
        }

        if (intent?.action == PERIODIC_REFRESH) {
            val restService = RestService(context!!)
            restService.updateAllTasks(appWidgetManager, appWidgetIds)
        }
    }

    private fun handleIncrementCompletionsClick(
        context: Context?,
        intent: Intent,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        val restService = RestService(context!!)
        val updatedTask = intent.getIntExtra("CLICKED_ID", -1)
        val updatedCompletions = 1 + intent.getIntExtra("CLICKED_COMPLETIONS", -999)

        restService.updateTaskCompletions(updatedTask, updatedCompletions, appWidgetManager, appWidgetIds)
    }

    private fun handleOpenActivityClick(context: Context?) {
        var newIntent = Intent("android.intent.action.ViewTaskInstancesActivity")
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context?.startActivity(newIntent)
    }

    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {

        // passes data to the remote adapter
        val rvWidget = RemoteViews(context?.packageName, R.layout.widget_view)
        val widgetDataIntent = Intent(context, TaskWidgetService::class.java)
        rvWidget.setRemoteAdapter(R.id.widgetCollectionList, widgetDataIntent)

        // generic pending intent passed to adapter
        var clickItemIntent = Intent(context, TaskWidgetProvider::class.java)
        clickItemIntent.setAction(ACTION_CLICK)
        var clickPendingIntent = PendingIntent.getBroadcast(context, 0, clickItemIntent, 0)
        rvWidget.setPendingIntentTemplate(R.id.widgetCollectionList, clickPendingIntent)

        // open activity only works for the header for some reason
        // need to figure out how to open activity on listview item click
        var openActivityIntent = Intent(context, ViewTaskInstancesActivity::class.java)
        var openActivityPendingIntent = PendingIntent.getActivity(context, 0, openActivityIntent, 0)
        rvWidget.setOnClickPendingIntent(R.id.widgetMain, openActivityPendingIntent)

        appWidgetManager?.updateAppWidget(appWidgetIds, rvWidget)

        backgroundUpdater(context)

        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }

    private fun backgroundUpdater(context: Context?) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, TaskWidgetProvider::class.java)
        intent.action = PERIODIC_REFRESH
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)

//      set recurring alarm with minimum values allowed by Android
        alarmManager.setRepeating(AlarmManager.RTC, 5000, 60000, pendingIntent)
    }
}
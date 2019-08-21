package com.example.tasktrackerclient.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import androidx.core.content.ContextCompat.startActivity
import com.example.tasktrackerclient.R
import com.example.tasktrackerclient.viewinstances.ViewTaskInstancesActivity

class TaskWidgetProvider : AppWidgetProvider() {

    companion object {
        val ACTION_CLICK = "com.example.tasktrackerclient.widget.click"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        var name = ComponentName(context, TaskWidgetProvider::class.java)
        var appWidgetIds : IntArray = AppWidgetManager.getInstance(context).getAppWidgetIds(name)
        if (appWidgetIds.isEmpty()) return

        var appWidgetManager = AppWidgetManager.getInstance(context)

        if (intent?.action == ACTION_CLICK) {
            var clickType = intent.getIntExtra("CLICK_TYPE", 0)
            // generic click means open the view task instances activity
            if (clickType == 0) {
                var newIntent = Intent("android.intent.action.ViewTaskInstancesActivity")
                newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context?.startActivity(newIntent)
            }
            if (clickType == 1) {
                // handle click please
                Log.d("appwidget", "handling click?")

            }
        }
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
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }
}
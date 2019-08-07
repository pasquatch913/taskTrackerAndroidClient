package com.example.tasktrackerclient.widget

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.example.tasktrackerclient.R
import com.example.tasktrackerclient.viewinstances.ViewTaskInstancesActivity


class MyAppWidgetProvider : AppWidgetProvider() {

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            val views = RemoteViews(context.packageName, R.layout.widget_view)

            val dataIntent = Intent(context, WidgetService::class.java)
            views.setRemoteAdapter(R.id.widgetCollectionList, dataIntent)

            val clickIntent = Intent(context, ViewTaskInstancesActivity::class.java)
            val pendingIntent = TaskStackBuilder.create(context)
                .addNextIntentWithParentStack(clickIntent)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
            views.setOnClickPendingIntent(R.id.widgetTaskName, pendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

}
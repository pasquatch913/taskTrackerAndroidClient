package com.example.tasktrackerclient.widget.thirdStab

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.example.tasktrackerclient.R

class TaskWidgetProvider: AppWidgetProvider() {

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
    }

    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {

        val rvWidget = RemoteViews(context?.packageName, R.layout.widget_view)
        val intent = Intent(context, TaskWidgetService::class.java)
        rvWidget.setRemoteAdapter(R.id.widgetCollectionList, intent)
        appWidgetManager?.updateAppWidget(appWidgetIds, rvWidget)

        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }
}
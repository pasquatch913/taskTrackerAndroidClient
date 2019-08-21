package com.example.tasktrackerclient.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.example.tasktrackerclient.R
import com.example.tasktrackerclient.viewinstances.ViewTaskInstancesActivity

class TaskWidgetProvider: AppWidgetProvider() {

    companion object {
        val EXTRA_ITEM_POSITION = "extraItemPosition"
        val ACTION_TOAST = "actionToast"
    }

    override fun onReceive(context: Context?, intent: Intent?) {

        super.onReceive(context, intent)
    }

    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {

        val rvWidget = RemoteViews(context?.packageName, R.layout.widget_view)
        val intent = Intent(context, TaskWidgetService::class.java)
        rvWidget.setRemoteAdapter(R.id.widgetCollectionList, intent)

        // open activity only works for the header for some reason
        // need to figure out how to open activity on listview item click
        var openActivityIntent = Intent(context, ViewTaskInstancesActivity::class.java)
        var openActivityPendingIntent = PendingIntent.getActivity(context, 0, openActivityIntent, 0)
        rvWidget.setOnClickPendingIntent(R.id.widgetMain, openActivityPendingIntent)
        rvWidget.setPendingIntentTemplate(R.id.widgetCollectionList, openActivityPendingIntent)

        appWidgetManager?.updateAppWidget(appWidgetIds, rvWidget)

//        var clickIntent = Intent(context, TaskWidgetProvider::class.java)
//        clickIntent.setAction(ACTION_TOAST)
//        var clickPendingIntent = PendingIntent.getBroadcast(context, 0, clickIntent, 0)
//



        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }
}
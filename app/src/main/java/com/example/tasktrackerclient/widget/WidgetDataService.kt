package com.example.tasktrackerclient.widget

import android.app.PendingIntent
import android.app.Service
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import com.example.tasktrackerclient.R
import kotlin.random.Random

class WidgetDataService : Service() {

    override fun onStart(intent: Intent?, startId: Int) {
        var appWidgetManager = AppWidgetManager.getInstance(this.applicationContext)
        var allWidgetIds = intent!!.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_ID)

        for (widget in allWidgetIds) {
            var number = Random.nextInt(100)
            var remoteViews = RemoteViews(
                this.applicationContext.packageName,
                R.layout.widget_view
            )
            Log.w("WidgetExample", number.toString())
            remoteViews.setTextViewText(R.id.widgetTaskName, number.toString())

            var clickIntent = Intent(this.applicationContext, MyAppWidgetProvider::class.java)
            clickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
            clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds)

            var pendingIntent = PendingIntent.getBroadcast(
                applicationContext, 0,
                clickIntent, PendingIntent.FLAG_UPDATE_CURRENT
            )
            remoteViews.setOnClickPendingIntent(R.id.widgetTaskName, pendingIntent)
            appWidgetManager.updateAppWidget(widget, remoteViews)
        }
        stopSelf()

        super.onStart(intent, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
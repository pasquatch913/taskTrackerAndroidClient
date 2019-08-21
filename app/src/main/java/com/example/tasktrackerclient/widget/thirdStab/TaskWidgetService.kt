package com.example.tasktrackerclient.widget.thirdStab

import android.content.Intent
import android.widget.RemoteViewsService

class TaskWidgetService: RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return TaskRemoteViewsFactory(this)
    }
}
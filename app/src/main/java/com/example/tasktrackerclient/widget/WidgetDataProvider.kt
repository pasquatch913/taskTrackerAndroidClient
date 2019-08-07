package com.example.tasktrackerclient.widget

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService.RemoteViewsFactory


class WidgetDataProvider(context: Context, intent: Intent) : RemoteViewsFactory {

    internal var mCollections: MutableList<String> = mutableListOf()

    internal var mContext: Context? = null

    init {
        mContext = context
    }

    override fun getCount(): Int {
        return mCollections.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewAt(position: Int): RemoteViews {
        val mView = RemoteViews(
            mContext!!.getPackageName(),
            android.R.layout.simple_list_item_1
        )
        mView.setTextViewText(android.R.id.text1, mCollections[position])

        return mView
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun onCreate() {
        initData()
    }

    override fun onDataSetChanged() {
        initData()
    }

    private fun initData() {
        mCollections.clear()
        for (i in 1..10) {
            mCollections.add("ListView item $i")
        }
    }

    override fun onDestroy() {

    }

}

package com.example.tasktrackerclient.widget

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tasktrackerclient.R
import kotlinx.android.synthetic.main.widget_row.view.*

class WidgetAdapter(private val data: ArrayList<String>) : RecyclerView.Adapter<WidgetAdapter.MyViewHolder>() {

    class MyViewHolder(val itemview: View) : RecyclerView.ViewHolder(itemview)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        // create a new view
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.widget_row, parent, false)
        return MyViewHolder(cellForRow)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.itemview.widgetTaskName.text = data[position]
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = data.size
}
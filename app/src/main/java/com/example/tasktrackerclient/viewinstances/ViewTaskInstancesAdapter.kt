package com.example.tasktrackerclient.viewinstances

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tasktrackerclient.R
import com.example.tasktrackerclient.TaskDTO

class ViewTaskInstancesAdapter(
    var data: List<TaskDTO>,
    val context: Context,
    val incrementClickListener: (View) -> Unit,
    val decrementClickListener: (View) -> Unit,
    val deactivateClickListener: (View) -> Unit
) :
    androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {


    override fun getItemCount(): Int {
        return data.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.task_instance_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        (holder as CustomViewHolder).bind(
            data[position],
            incrementClickListener,
            decrementClickListener,
            deactivateClickListener
        )
    }

    fun removeTaskFromView(id: Int) {
        data = data.filter { i -> i.id != id }
        notifyDataSetChanged()
    }

}
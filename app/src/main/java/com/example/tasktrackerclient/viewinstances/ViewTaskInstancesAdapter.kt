package com.example.tasktrackerclient.viewinstances

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tasktrackerclient.R
import com.example.tasktrackerclient.TaskDTO
import com.example.tasktrackerclient.rest.CustomViewHolder

class ViewTaskInstancesAdapter(var data: List<TaskDTO>, val context: Context, val clickListener: (View) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun getItemCount(): Int {
        return data.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.task_instance_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CustomViewHolder).bind(data[position], clickListener)
    }

}
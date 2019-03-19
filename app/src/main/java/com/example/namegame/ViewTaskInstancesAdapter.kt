package com.example.namegame

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.task_row.view.*


class ViewTaskInstancesAdapter(data: List<OneTimeTaskEntity>): RecyclerView.Adapter<CustomViewHolder>() {

    var data = data

    override fun getItemCount(): Int {
        return data.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.task_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val task = data[position]
        holder.view.taskName.text = task.name
        holder.view.taskDueDate.text = task.dueDate.toString()
        holder.view.taskCompletions.text = task.completions.toString()
        holder.view.taskPoints.text = task.weight.toString()

    }
}
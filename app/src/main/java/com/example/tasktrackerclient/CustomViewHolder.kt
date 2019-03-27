package com.example.tasktrackerclient

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.task_instance_row.view.*

class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(task: OneTimeTaskEntity, clickListener: (View) -> Unit) {
        itemView.taskId.text = task.id.toString()
        itemView.taskName.text = task.name
        itemView.taskDueDate.text = task.dueDate.toString()
        itemView.taskCompletions.text = task.completions.toString()
        itemView.taskPoints.text = task.weight.toString()

        itemView.incrementTask.setOnClickListener { clickListener(itemView) }
    }
}
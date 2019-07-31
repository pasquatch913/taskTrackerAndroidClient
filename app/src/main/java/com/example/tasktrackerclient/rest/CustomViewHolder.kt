package com.example.tasktrackerclient.rest

import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.tasktrackerclient.TaskDTO
import kotlinx.android.synthetic.main.task_instance_row.view.*

class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(
        task: TaskDTO, incrementClickListener: (View) -> Unit,
        decrementClickListener: (View) -> Unit,
        unsubscribeClickListener: (View) -> Unit
    ) {
        itemView.taskId.text = task.id.toString()
        itemView.taskName.text = task.name
        itemView.taskDueDate.text = task.dueDate.toString()
        itemView.taskCompletions.text = task.completions.toString()
        itemView.taskPoints.text = task.weight.toString()

        itemView.incrementTask.setOnClickListener { incrementClickListener(itemView) }
        itemView.decrementTask.setOnClickListener { decrementClickListener(itemView) }
        itemView.unsubscribeTask.setOnClickListener { unsubscribeClickListener(itemView) }

        if (task.recurring) {
            itemView.recurringLabel.visibility = View.VISIBLE
        }
    }
}
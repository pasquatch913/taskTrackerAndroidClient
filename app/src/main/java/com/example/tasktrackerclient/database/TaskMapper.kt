package com.example.tasktrackerclient.database

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import android.util.Log
import com.example.tasktrackerclient.TaskDTO
import java.time.LocalDate

class TaskMapper {

    fun taskDtoToTaskEntity(taskDTO: TaskDTO) : TaskEntity {
        return TaskEntity(taskDTO.id, taskDTO.name, taskDTO.completionsGoal, taskDTO.completions, taskDTO.weight,
            taskDTO.dueDate, taskDTO.active, taskDTO.recurring)
    }

//    fun taskEntityToTaskDTO(taskEntity: TaskEntity) : TaskDTO {
//        return TaskDTO(taskEntity.id, taskEntity.name, taskEntity.completionsGoal, taskEntity.completions, taskEntity.weight,
//            taskEntity.dueDate, taskEntity.active, taskEntity.recurring)
//    }

    fun cursorToTaskEntity(cursor: Cursor): TaskEntity {
        return TaskEntity(
            cursor?.getInt(cursor.getColumnIndex(DbHelper.COLUMN_ID)),
            cursor?.getString(cursor.getColumnIndex(DbHelper.COLUMN_NAME)),
            cursor?.getInt(cursor.getColumnIndex(DbHelper.COLUMN_COMP_GOAL)),
            cursor?.getInt(cursor.getColumnIndex(DbHelper.COLUMN_COMPS)),
            cursor?.getInt(cursor.getColumnIndex(DbHelper.COLUMN_WEIGHT)),
            LocalDate.parse(cursor?.getString(cursor.getColumnIndex(DbHelper.COLUMN_DUE_DATE))),
            cursor?.getString(cursor.getColumnIndex(DbHelper.COLUMN_ACTIVE)) == "True",
            cursor?.getString(cursor.getColumnIndex(DbHelper.COLUMN_RECURRING)) == "True"
        )
    }

    fun taskEntityToContentValues(taskEntity: TaskEntity): ContentValues {
        val values = ContentValues()
        values.put(DbHelper.COLUMN_ID, taskEntity.id)
        values.put(DbHelper.COLUMN_NAME, taskEntity.name)
        values.put(DbHelper.COLUMN_COMP_GOAL, taskEntity.completionsGoal)
        values.put(DbHelper.COLUMN_COMPS, taskEntity.completions)
        values.put(DbHelper.COLUMN_WEIGHT, taskEntity.weight)
        values.put(DbHelper.COLUMN_DUE_DATE, taskEntity.dueDate.toString())
        values.put(DbHelper.COLUMN_ACTIVE, taskEntity.active.toString())
        values.put(DbHelper.COLUMN_RECURRING, taskEntity.recurring.toString())
        Log.d("inserter", "task ID ${taskEntity.id} and name ${taskEntity.name} inserted")
        return values
    }
}
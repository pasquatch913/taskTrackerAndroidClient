package com.example.tasktrackerclient.database

import android.content.ContentValues
import android.provider.BaseColumns
import com.example.tasktrackerclient.TaskDTO
import java.time.LocalDate

class TaskMapper {

    fun taskDtoToTaskEntity(taskDTO: TaskDTO) : TaskEntity {
//        return TaskEntity(taskDTO.id, taskDTO.name, taskDTO.completionsGoal, taskDTO.completions, taskDTO.weight,
//            taskDTO.dueDate, taskDTO.active, taskDTO.recurring)
        return TaskEntity(taskDTO.id, taskDTO.name)
    }

//    fun taskEntityToTaskDTO(taskEntity: TaskEntity) : TaskDTO {
//        return TaskDTO(taskEntity.id, taskEntity.name, taskEntity.completionsGoal, taskEntity.completions, taskEntity.weight,
//            taskEntity.dueDate, taskEntity.active, taskEntity.recurring)
//    }

//    fun contentValuesToTaskEntity(contentValues: ContentValues) : TaskEntity {
//        return TaskEntity(contentValues.getAsInteger("id"),
//            contentValues.getAsString("name"))
////            contentValues.getAsInteger("completions_goal"),
////            contentValues.getAsInteger("completions"),
////            contentValues.getAsInteger("weight"),
////            LocalDate.parse(contentValues.getAsString("due_date")),
////            contentValues.getAsBoolean("active"),
////            contentValues.getAsBoolean("recurring"))
//    }

//    fun taskEntityToContentValues


//
//    @Mapping(source = "id", target = "remoteId")
//    fun contentValuesToTaskEntity(values: ContentValues): TaskEntity
//
//    //    might also need mapper from entity to content values???
//    @Mapping(source = "id", target = "remoteId")
//    fun taskEntityToContentValues(task: TaskEntity): ContentValues
}
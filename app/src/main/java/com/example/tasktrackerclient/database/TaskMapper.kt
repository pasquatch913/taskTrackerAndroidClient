package com.example.tasktrackerclient.database

import android.content.ContentValues
import com.example.tasktrackerclient.TaskDTO
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper
interface TaskMapper {

    @Mapping(source = "id", target = "remoteId")
    fun taskDtoToTaskEntity(taskDTO: TaskDTO): TaskEntity

    @Mappings(
        Mapping(source = "remoteId", target = "id")
    )
    fun taskEntityToTaskDTO(taskEntity: TaskEntity): TaskDTO

    @Mapping(source = "id", target = "remoteId")
    fun contentValuesToTaskEntity(values: ContentValues): TaskEntity

    //    might also need mapper from entity to content values???
    @Mapping(source = "id", target = "remoteId")
    fun taskEntityToContentValues(task: TaskEntity): ContentValues
}
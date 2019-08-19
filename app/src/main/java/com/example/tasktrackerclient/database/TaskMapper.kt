package com.example.tasktrackerclient.database

import com.example.tasktrackerclient.TaskDTO
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper
interface TaskMapper {

    @Mapping(source = "id", target = "remoteId")
    fun taskDtoToTaskEntity(taskDTO: TaskDTO) : TaskEntity

    @Mappings(
        Mapping(source = "remoteId", target = "id")
    )
    fun taskEntityToTaskDTO(taskEntity: TaskEntity) : TaskDTO
}
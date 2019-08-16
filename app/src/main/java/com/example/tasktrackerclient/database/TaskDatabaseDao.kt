package com.example.tasktrackerclient.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.tasktrackerclient.TaskDTO

@Dao
interface TaskDatabaseDao {

    @Insert
    fun insert(task: TaskDTO)

    // update method must update task values only
    @Update
    fun update(task: TaskDTO)

    @Query("SELECT * from task_table WHERE remoteId = :key")
    fun get(key: Long): TaskDTO?

    //delete method will set active to false

    //another method can be used to sync with remote server data (or maybe this is more abstract)
    // but basic flow will be to set the task ID
}
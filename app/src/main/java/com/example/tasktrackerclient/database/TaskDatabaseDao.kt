package com.example.tasktrackerclient.database

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDatabaseDao {

    @Insert
    fun insert(task: TaskEntity)

    @Insert
    fun insertAll(taskList: List<TaskEntity>): List<Long>

    // update method must update task values only
    @Update
    fun update(task: TaskEntity)

    @Query("SELECT * from task_table WHERE remoteId = :key")
    fun get(key: Long): TaskEntity?

    @Query("SELECT * FROM task_table")
    fun selectAll(): Cursor

    //delete method will set active to false

    //another method can be used to sync with remote server data (or maybe this is more abstract)
    // but basic flow will be to set the task ID
}
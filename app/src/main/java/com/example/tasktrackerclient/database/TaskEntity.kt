package com.example.tasktrackerclient.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "task_table")
data class TaskEntity (
    @PrimaryKey(autoGenerate = true)
    val localId: Int,
    val remoteId: Int,
    val name: String,
    val completionsGoal: Int,
    val completions: Int,
    val weight: Int,
    val dueDate: LocalDate,
    val active: Boolean,
    val recurring: Boolean
    )
package com.example.tasktrackerclient

import java.time.LocalDate

class TaskDTO(
    val id: Int,
    val name: String,
    val completionsGoal: Int,
    val completions: Int,
    val weight: Int,
    val dueDate: LocalDate,
    val active: Boolean
)
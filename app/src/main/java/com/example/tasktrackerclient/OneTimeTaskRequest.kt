package com.example.tasktrackerclient

import java.time.LocalDate

class OneTimeTaskRequest(

    val id: Int,
    val name: String,
    val weight: Int,
    val completionsGoal: Int,
    val completions: Int = 0,
    val dueDate: LocalDate,
    val active: Boolean = true

)
package com.example.tasktrackerclient.rest

import java.time.LocalDate

class TaskInstanceResponse (
    val id: Int,
    val name: String,
    val necessaryCompletions: Int,
    val completions: Int,
    val weight: Int,
    val dueDate: LocalDate,
    val active: Boolean,
    val recurring: Boolean
)
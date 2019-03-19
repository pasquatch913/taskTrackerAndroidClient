package com.example.tasktrackerclient

import java.time.LocalDate

class OneTimeTaskEntity(
    val id: Int,
    val name: String,
    val weight: Int,
    val necessaryCompletions: Int,
    val completions: Int,
    val dueDate: LocalDate,
    val active: Boolean
)
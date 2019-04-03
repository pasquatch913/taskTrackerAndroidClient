package com.example.tasktrackerclient.viewinstances

import java.time.LocalDate

class TaskInstanceEntity(

    val id: Int,
    val name: String,
    val weight: Int,
    val necessaryCompletions: Int,
    val completions: Int,
    val dueDate: LocalDate,
    val taskInstanceId: Int,
    val active: Boolean


)
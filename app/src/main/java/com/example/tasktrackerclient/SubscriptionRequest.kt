package com.example.tasktrackerclient

class SubscriptionRequest(

    val id: Int,
    val name: String,
    val completionsGoal: Int,
    val weight: Int,
    val period: TaskPeriod,
    val active: Boolean = true

)
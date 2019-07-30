package com.example.tasktrackerclient

class SubscriptionRequest(

    val name: String,
    val necessaryCompletions: Int,
    val weight: Int,
    val period: TaskPeriod,
    val active: Boolean = true

)
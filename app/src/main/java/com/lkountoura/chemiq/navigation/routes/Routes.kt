package com.lkountoura.chemiq.navigation.routes

import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
object Review

@Serializable
object Progress

@Serializable
object Settings

@Serializable
data class TopicSelect(val game: String)

@Serializable
data class Question(val topicId: Int, val difficulty: String)

@Serializable
data class Results(val correct: Int, val total: Int)

@Serializable
object PeriodicTable

@Serializable
object Balancing

@Serializable
object Matching

@Serializable
object TimeAttack
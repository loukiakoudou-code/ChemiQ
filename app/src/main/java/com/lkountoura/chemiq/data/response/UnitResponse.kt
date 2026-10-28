package com.lkountoura.chemiq.data.response

import kotlinx.serialization.Serializable

@Serializable
data class UnitResponse(
    val id: Int,
    val title: String,
    val grade: String,
    val sortOrder: Int,
)

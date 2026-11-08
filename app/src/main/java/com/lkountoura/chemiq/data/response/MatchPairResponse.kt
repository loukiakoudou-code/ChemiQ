package com.lkountoura.chemiq.data.response

import kotlinx.serialization.Serializable

@Serializable
data class MatchPairResponse(
    val id: Int,
    val unitId: Int,
    val name: String,
    val formula: String
) {
}

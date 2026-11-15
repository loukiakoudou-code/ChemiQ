package com.lkountoura.chemiq.data.response

import kotlinx.serialization.Serializable

@Serializable
data class EquationResponse(
    val id: Int,
    val unitId: Int,
    val difficulty: Int,
    val reactants: String,
    val products: String,
    val solution: String
)

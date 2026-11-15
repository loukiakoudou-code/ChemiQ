package com.lkountoura.chemiq.data.dto

data class EquationDTO(
    val id: Int,
    val unitId: Int,
    val difficulty: Int,
    val reactants: List<String>,
    val products: List<String>,
    val solution: List<Int>
)
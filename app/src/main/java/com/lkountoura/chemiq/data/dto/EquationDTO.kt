package com.lkountoura.chemiq.data.dto

data class EquationDTO(
    val id: Int,
    val unitId: Int,
    val difficulty: Int,
    val reactants: List<SpeciesDTO>,
    val products: List<SpeciesDTO>
)

data class SpeciesDTO(
    val formula: String,
    val atoms: Map<String, Int>,
    val solution: Int
)
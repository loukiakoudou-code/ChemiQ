package com.lkountoura.chemiq.converter

import com.lkountoura.chemiq.data.response.EquationResponse
import com.lkountoura.chemiq.database.entities.EquationEntity

class EquationConverter {
    fun convert(
        equationResponses: List<EquationResponse>
    ): List<EquationEntity> {
        return equationResponses.map { response ->
            EquationEntity.build(
                id = response.id,
                unitId = response.unitId,
                difficulty = response.difficulty,
                reactants = response.reactants,
                products = response.products,
                solution = response.solution
            )
        }
    }
}
package com.lkountoura.chemiq.converter.dtoConverter

import com.lkountoura.chemiq.data.dto.EquationDTO
import com.lkountoura.chemiq.database.entities.EquationEntity

class EquationDTOConverter {
    fun convert(
        listOfEquationEntities: List<EquationEntity>
    ): List<EquationDTO> {
        return listOfEquationEntities.map { entity ->
            EquationDTO(
                id = entity.id,
                unitId = entity.unitId,
                difficulty = entity.difficulty,
                reactants = entity.reactants.split(","),
                products = entity.products.split(","),
                solution = entity.solution.split(",").map { it.toInt() }
            )
        }
    }
}

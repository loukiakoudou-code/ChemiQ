package com.lkountoura.chemiq.converter.dtoConverter

import com.lkountoura.chemiq.data.dto.EquationDTO
import com.lkountoura.chemiq.data.dto.SpeciesDTO
import com.lkountoura.chemiq.database.entities.EquationEntity

class EquationDTOConverter {
    fun convert(
        listOfEquationEntities: List<EquationEntity>
    ): List<EquationDTO> {
        return listOfEquationEntities.map { entity ->
            val reactantFormulas = entity.reactants.split(",")
            val productFormulas = entity.products.split(",")
            val solution = entity.solution.split(",").map { it.toInt() }

            EquationDTO(
                id = entity.id,
                unitId = entity.unitId,
                difficulty = entity.difficulty,
                reactants = reactantFormulas.mapIndexed { i, formula ->
                    SpeciesDTO(
                        formula = formula,
                        atoms = countAtoms(formula),
                        solution = solution[i]
                    )
                },
                products = productFormulas.mapIndexed { i, formula ->
                    SpeciesDTO(
                        formula = formula,
                        atoms = countAtoms(formula),
                        solution = solution[reactantFormulas.size + i]
                    )
                }
            )
        }
    }

    private fun countAtoms(formula: String): Map<String, Int> {
        val result = mutableMapOf<String, Int>()
        var i = 0

        while (i < formula.length) {
            if (formula[i] == '(') {

                val close = formula.indexOf(')', i)
                val inside = countAtoms(formula.substring(i + 1, close))
                val (number, next) = readNumber(formula, close + 1)
                inside.forEach { (atom, count) ->
                    result[atom] = (result[atom] ?: 0) + count * number
                }
                i = next
            } else {

                var atom = formula[i].toString()
                i++
                if (i < formula.length && formula[i].isLowerCase()) {
                    atom += formula[i]
                    i++
                }
                val (number, next) = readNumber(formula, i)
                result[atom] = (result[atom] ?: 0) + number
                i = next
            }
        }
        return result
    }

    private fun readNumber(formula: String, start: Int): Pair<Int, Int> {
        var i = start
        while (i < formula.length && formula[i].isDigit()) i++
        val number = if (i == start) 1 else formula.substring(start, i).toInt()
        return Pair(number, i)
    }


}

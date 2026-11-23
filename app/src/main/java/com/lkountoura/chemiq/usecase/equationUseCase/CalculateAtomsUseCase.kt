package com.lkountoura.chemiq.usecase.equationUseCase

import com.lkountoura.chemiq.data.dto.EquationDTO
import com.lkountoura.chemiq.data.dto.SpeciesDTO
import javax.inject.Inject

data class AtomCountDTO(
    val atom: String,
    val reactants: Int,
    val products: Int
) {
    val isBalanced: Boolean
        get() = reactants == products
}

interface CalculateAtomsUseCase {
    fun execute(equation: EquationDTO, coefficients: List<Int>): List<AtomCountDTO>
}

class CalculateAtomsUseCaseImpl @Inject constructor() : CalculateAtomsUseCase {

    override fun execute(equation: EquationDTO, coefficients: List<Int>): List<AtomCountDTO> {
        val left = countSide(equation.reactants, coefficients.take(equation.reactants.size))
        val right = countSide(equation.products, coefficients.drop(equation.reactants.size))
        return left.keys.map { atom ->
            AtomCountDTO(
                atom = atom,
                reactants = left[atom] ?: 0,
                products = right[atom] ?: 0
            )
        }
    }

    private fun countSide(species: List<SpeciesDTO>, coefficients: List<Int>): Map<String, Int> {
        val total = mutableMapOf<String, Int>()
        species.forEachIndexed { i, item ->
            item.atoms.forEach { (atom, count) ->
                total[atom] = (total[atom] ?: 0) + count * coefficients[i]
            }
        }
        return total
    }
}
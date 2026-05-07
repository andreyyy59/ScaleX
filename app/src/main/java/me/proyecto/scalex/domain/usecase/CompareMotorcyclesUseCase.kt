package me.proyecto.scalex.domain.usecase

import me.proyecto.scalex.domain.model.Motorcycle
import javax.inject.Inject
import kotlin.math.abs

class CompareMotorcyclesUseCase @Inject constructor() {

    data class ComparisonResult(
        val motorcycle1: Motorcycle,
        val motorcycle2: Motorcycle,
        val similarityScore: Double,
        val differences: Map<String, Pair<String?, String?>>
    )

    fun execute(moto1: Motorcycle, moto2: Motorcycle): ComparisonResult {
        val score = calculateSimilarity(moto1, moto2)
        val differences = mapOf(
            "Peso" to (moto1.totalWeight to moto2.totalWeight),
            "Altura" to (moto1.totalHeight to moto2.totalHeight),
            "Longitud" to (moto1.totalLength to moto2.totalLength),
            "Anchura" to (moto1.totalWidth to moto2.totalWidth),
            "Cilindrada" to (moto1.displacement to moto2.displacement),
            "Potencia" to (moto1.power to moto2.power),
            "Torque" to (moto1.torque to moto2.torque)
        )

        return ComparisonResult(
            motorcycle1 = moto1,
            motorcycle2 = moto2,
            similarityScore = score,
            differences = differences
        )
    }

    fun findSimilar(base: Motorcycle, candidates: List<Motorcycle>, limit: Int = 10): List<Motorcycle> {
        return candidates
            .filter { it.getId() != base.getId() }
            .map { bike -> bike to calculateSimilarity(base, bike) }
            .sortedByDescending { it.second }
            .take(limit)
            .map { it.first }
    }

    private fun calculateSimilarity(base: Motorcycle, compare: Motorcycle): Double {
        var score = 0.0
        var factors = 0

        base.getLengthInMm()?.let { baseLength ->
            compare.getLengthInMm()?.let { compareLength ->
                val diff = abs(baseLength - compareLength)
                score += (1.0 - (diff / baseLength).coerceIn(0.0F, 1.0F)) * 0.15
                factors++
            }
        }

        base.getWidthInMm()?.let { baseWidth ->
            compare.getWidthInMm()?.let { compareWidth ->
                val diff = abs(baseWidth - compareWidth)
                score += (1.0 - (diff / baseWidth).coerceIn(0.0F, 1.0F)) * 0.15
                factors++
            }
        }

        base.getHeightInMm()?.let { baseHeight ->
            compare.getHeightInMm()?.let { compareHeight ->
                val diff = abs(baseHeight - compareHeight)
                score += (1.0 - (diff / baseHeight).coerceIn(0.0F, 1.0F)) * 0.10
                factors++
            }
        }

        base.getWeightInKg()?.let { baseWeight ->
            compare.getWeightInKg()?.let { compareWeight ->
                val diff = abs(baseWeight - compareWeight)
                score += (1.0 - (diff / baseWeight).coerceIn(0.0F, 1.0F)) * 0.20
                factors++
            }
        }

        base.getDisplacementInCC()?.let { baseDisp ->
            compare.getDisplacementInCC()?.let { compareDisp ->
                val diff = abs(baseDisp - compareDisp)
                score += (1.0 - (diff / baseDisp).coerceIn(0.0F, 1.0F)) * 0.20
                factors++
            }
        }

        base.getPowerInHP()?.let { basePower ->
            compare.getPowerInHP()?.let { comparePower ->
                val diff = abs(basePower - comparePower)
                score += (1.0 - (diff / basePower).coerceIn(0.0F, 1.0F)) * 0.20
                factors++
            }
        }

        return if (factors > 0) score else 0.0
    }
}

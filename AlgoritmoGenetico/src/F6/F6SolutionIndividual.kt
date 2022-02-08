package F6

import model.BinaryWordIndividual
import model.EvaluationFunction
import kotlin.math.pow

class F6SolutionIndividual(evaluationFunction: EvaluationFunction,
                           crossOverChance: Double,
                           mutationChance: Double,
                           genes: List<String>)
    : BinaryWordIndividual(evaluationFunction, crossOverChance, mutationChance, genes) {

    override fun representation(): String {
        return "x=${getX()}, y=${getY()}"
    }

    override fun createFromGenes(genes: List<String>): F6SolutionIndividual {
        return F6SolutionIndividual(evaluationFunction, crossOverChance, mutationChance, genes)
    }

    fun getX(): Double = geneToDouble(0)

    fun getY(): Double = geneToDouble(1)

    private fun geneToDouble(index: Int): Double = genes[index].toInt(2) * (200 / (2.0.pow(22) - 1)) - 100
}
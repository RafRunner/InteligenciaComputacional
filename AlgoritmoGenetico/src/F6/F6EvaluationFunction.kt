package F6

import model.BinaryWordIndividual
import model.EvaluationFunction
import model.Individual
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class F6EvaluationFunction: EvaluationFunction {

    private fun f6Function(x: Double, y: Double): Double {
        return 0.5 - (sin(sqrt( x.pow(2) + y.pow(2))).pow(2) - 0.5) / (1.0 + 0.001 * (x.pow(2) + y.pow(2))).pow(2)
    }

    private fun geneToDouble(gene: String): Double {
        return gene.toInt(2) * (200 / (2.0.pow(22) - 1)) - 100
    }

    override fun evaluate(individual: Individual): Double {
        if (individual !is BinaryWordIndividual) {
            throw java.lang.RuntimeException("Can only evaluate BinaryWordIndividual")
        }

        val x = geneToDouble(individual.geneToString(0))
        val y = geneToDouble(individual.geneToString(1))

        return f6Function(x, y)
    }
}
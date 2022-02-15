package F6

import model.EvaluationFunction
import model.Individual
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class F6EvaluationFunction: EvaluationFunction {

    private fun f6Function(x: Double, y: Double): Double {
        return 0.5 - (sin(sqrt( x.pow(2) + y.pow(2))).pow(2) - 0.5) / (1.0 + 0.001 * (x.pow(2) + y.pow(2))).pow(2)
    }

    override fun evaluate(individual: Individual): Double {
        if (individual !is F6SolutionIndividual) {
            throw RuntimeException("Can only evaluate F6SolutionIndividual")
        }

        val x = individual.getX()
        val y = individual.getY()

        return f6Function(x, y)
    }
}
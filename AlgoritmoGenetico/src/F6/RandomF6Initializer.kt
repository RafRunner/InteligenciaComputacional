package F6

import model.IndividualInitialization
import model.RandomBinaryGeneInitializer

class RandomF6Initializer(private val crossOverChance: Double,
                          private val mutationChance: Double) : IndividualInitialization {

    init {
        if (crossOverChance < 0.0 || crossOverChance > 100.0) {
            throw RuntimeException("crossOverChance must be between 0.0 and 100.0")
        }
        if (mutationChance < 0.0 || mutationChance > 100.0) {
            throw RuntimeException("mutationChance must be between 0.0 and 100.0")
        }
    }

    companion object {
        val evaluationFunction = F6EvaluationFunction()
        val geneInitializer = RandomBinaryGeneInitializer(2, 22)
    }

    override fun initialize(): F6SolutionIndividual {
        return F6SolutionIndividual(evaluationFunction, crossOverChance, mutationChance, geneInitializer.initialize())
    }
}
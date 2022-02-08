package F6

import model.IndividualInitialization
import model.RandomBinaryGeneInitializer

class RandomF6Initializer(private val crossOverChance: Double,
                          private val mutationChance: Double) : IndividualInitialization {

    companion object {
        val evaluationFunction = F6EvaluationFunction()
        val geneInitializer = RandomBinaryGeneInitializer(2, 22)
    }

    override fun initialize(): F6SolutionIndividual {
        return F6SolutionIndividual(evaluationFunction, crossOverChance, mutationChance, geneInitializer.initialize())
    }
}
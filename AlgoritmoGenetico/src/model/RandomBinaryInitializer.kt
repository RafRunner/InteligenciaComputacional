package model

class RandomBinaryInitializer(private val evaluationFunction: EvaluationFunction,
                              private val mutationChance: Double,
                              private val wordCount: Int,
                              private val wordSize: Int) : IndividualInitialization {

    override fun initialize(): Individual {
        val genes = List(wordCount) {
            List(wordSize) {
                Math.random() > 0.5
            }
        }
        return BinaryWordIndividual(evaluationFunction, mutationChance, genes)
    }
}
package model

class GenerationStopCondition(private val maxGeneration: Int) : StopCondition {

    init {
        if (maxGeneration <= 0) {
            throw RuntimeException("maxGeneration must be positive")
        }
    }

    override fun shouldStop(population: Population): Boolean = population.currentGeneration >= maxGeneration
}
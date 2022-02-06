package model

class GenerationStopCondition(private val maxGeneration: Int) : StopCondition {
    override fun shouldStop(population: Population): Boolean = population.currentGeneration >= maxGeneration
}
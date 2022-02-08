package model

class Population(populationSize: Int,
                          initializationFunction: IndividualInitialization,
                          private val populationSelection: PopulationSelection,
                          private val stopCondition: StopCondition) {

    var currentGeneration: Int = 0
    var individuals: List<Individual>

    init {
        individuals = MutableList(populationSize) { initializationFunction.initialize() }
        evaluate()
    }

    private fun evaluate() {
        individuals.forEach { individual ->
            individual.evaluate()
        }
    }
    
    private fun generateNextGeneration() {
        currentGeneration++
        val parentsLists = populationSelection.selectParents(this, individuals.size)
        val children = parentsLists.flatMap { parents ->
            parents[0].crossover(parents[1])
        }
        val mutatedChildren = children.map { individual ->
            individual.mutate()
        }
        individuals = populationSelection.cutDownPopulation(this, mutatedChildren)
        evaluate()
    }

    fun train() {
        printStatistics()
        while (!stopCondition.shouldStop(this)) {
            generateNextGeneration()
            printStatistics()
        }
    }

    fun mostFitIndividual(): Individual {
        return individuals.maxByOrNull { individual -> individual.fitness }!!
    }

    private fun fitnessAverage(): Double {
        return individuals.sumOf { it.fitness } / individuals.size
    }

    private fun printStatistics() {
        println("Generation ${currentGeneration}:\t Average fitness: ${fitnessAverage()}.\t Best fitness score = ${mostFitIndividual().fitness}.\t Representation: ${mostFitIndividual().representation()}")
    }
}
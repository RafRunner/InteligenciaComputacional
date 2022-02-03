package model

abstract class Population(populationSize: Int,
                          initializationFunction: IndividualInitialization,
                          private val populationSelection: PopulationSelection,
                          private val stopCondition: StopCondition) {

    var currentGeneration: Int = 0
    val individuals: List<Individual>

    init {
        individuals = MutableList(populationSize) { initializationFunction.initialize() }
    }

    fun evaluate() {
        individuals.forEach { individual ->
            individual.evaluate()
        }
    }
    
    fun generateNextGeneration() {
        currentGeneration++
        val parentsLists = populationSelection.selectParents(this)
        parentsLists.forEach { parents ->
            parents.first().crossover(parents.subList(1, parents.size))
        }
        individuals.forEach { individual ->
            individual.mutate()
        }
        evaluate()
    }

    fun train() {
        while (!stopCondition.shouldStop(this)) {
            generateNextGeneration()
        }
    }

    fun mostFitIndividual(): Individual {
        return individuals.maxByOrNull { individual -> individual.fitness ?: Double.MIN_VALUE }!!
    }
}
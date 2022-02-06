package model

abstract class Population(populationSize: Int,
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
        val parentsLists = populationSelection.selectParents(this)
        var children = parentsLists.flatMap { parents ->
            parents[0].crossover(parents[1])
        }
        children = children.map { individual ->
            individual.mutate()
        }
        individuals = populationSelection.cutDownPopulation(individuals, children)
        evaluate()
    }

    fun train() {
        while (!stopCondition.shouldStop(this)) {
            generateNextGeneration()
        }
    }

    fun mostFitIndividual(): Individual {
        return individuals.maxByOrNull { individual -> individual.fitness!! }!!
    }
}
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
        var mostFitIndividual = mostFitIndividual()
        println("Generation ${currentGeneration}. Best fitness score = ${mostFitIndividual.fitness}. Representation: ${mostFitIndividual.representation()}")
        while (!stopCondition.shouldStop(this)) {
            generateNextGeneration()
            mostFitIndividual = mostFitIndividual()
            println("Generation ${currentGeneration}. Best fitness score = ${mostFitIndividual.fitness}. Representation: ${mostFitIndividual.representation()}")
        }
    }

    fun mostFitIndividual(): Individual {
        return individuals.maxByOrNull { individual -> individual.fitness }!!
    }
}
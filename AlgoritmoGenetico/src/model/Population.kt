package model

class Population(populationSize: Int,
                 initializationFunction: IndividualInitialization,
                 private val populationSelection: PopulationSelection,
                 private val stopCondition: StopCondition,
                 resolution: Int = 16) {

    var currentGeneration: Int = 0
    var individuals: List<Individual>

    private val doubleFormat: String

    init {
        doubleFormat = "%.${resolution}f"
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
            parents.first().crossover(parents.drop(1))
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

    private fun formatDouble(a: Double): String = String.format(doubleFormat, a)

    private fun printStatistics() {
        val mostFitIndividual = mostFitIndividual()
        println("Generation ${currentGeneration}:\t Average fitness: ${formatDouble(fitnessAverage())}.\t Best fitness = ${formatDouble(mostFitIndividual.fitness)}.\t Best solution: ${mostFitIndividual.representation()}")
    }
}
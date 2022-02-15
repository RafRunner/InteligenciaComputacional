package model

class Population(populationSize: Int,
                 initializationFunction: IndividualInitialization,
                 private val populationSelection: PopulationSelection,
                 private val stopCondition: StopCondition,
                 resolution: Int = 16) {

    var currentGeneration: Int = 0
    var individuals: List<Individual>
    val populationHistory: MutableList<PopulationReport> = mutableListOf()

    private val doubleFormat: String

    init {
        // A população deve ter uma quantidade positiva e par de indiívios (para selecionar os pares para o crossover)
        if (populationSize <= 0 || populationSize % 2 != 0) {
            throw RuntimeException("populationSize must be positive and even")
        }
        doubleFormat = "%.${resolution}f"
        // Usamos a função de inicialização para popular uma lista de indivíduos do tamanho desejado e então já avaliamos a população
        individuals = MutableList(populationSize) { initializationFunction.initialize() }
        evaluate()
    }

    private fun evaluate() {
        individuals.forEach { individual ->
            individual.evaluate()
        }
    }

    // Aumenta a geração, seleciona pais, faz crossover com os pares, muta os filhos, mata os pais e avalia a nova geração
    private fun generateNextGeneration() {
        currentGeneration++
        val parentsLists = populationSelection.selectParents(this)
        val children = parentsLists.flatMap { parents ->
            parents.first().crossover(parents.drop(1))
        }
        val mutatedChildren = children.map { individual ->
            individual.mutate()
        }
        individuals = populationSelection.cutDownPopulation(this, mutatedChildren)
        evaluate()
    }

    // Enquanto a condição de para não é satisfeita, geramos novas gerações e registramos os resultados
    fun train() {
        printAndRegisterStatistics()
        while (!stopCondition.shouldStop(this)) {
            generateNextGeneration()
            printAndRegisterStatistics()
        }
    }

    fun mostFitIndividual(): Individual {
        return individuals.maxByOrNull { individual -> individual.fitness }!!
    }

    private fun fitnessAverage(): Double {
        return individuals.sumOf { it.fitness } / individuals.size
    }

    private fun formatDouble(a: Double): String = String.format(doubleFormat, a)

    private fun printAndRegisterStatistics() {
        val mostFitIndividual = mostFitIndividual()
        val fitnessAverage = fitnessAverage()
        println("Generation ${currentGeneration}:\t Average fitness: ${formatDouble(fitnessAverage)}.\t Best fitness = ${formatDouble(mostFitIndividual.fitness)}.\t Best solution: ${mostFitIndividual.representation()}")
        populationHistory.add(PopulationReport(mostFitIndividual, fitnessAverage))
    }
}
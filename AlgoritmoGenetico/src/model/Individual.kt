package model

abstract class Individual(val evaluationFunction: EvaluationFunction) {

    var fitness: Double = -Double.MAX_VALUE

    // Essa função deve preencher a variável fitness e a retornar
    fun evaluate(): Double {
        fitness = evaluationFunction.evaluate(this)
        return fitness
    }

    abstract fun mutate(): Individual
    abstract fun crossover(partners: List<Individual>): List<Individual>

    abstract fun representation(): String
}
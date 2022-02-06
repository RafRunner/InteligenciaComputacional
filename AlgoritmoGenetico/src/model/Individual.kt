package model

abstract class Individual(val evaluationFunction: EvaluationFunction) {

    var fitness: Double? = null

    // Essa função deve preencher a variável fitness e a retornar
    fun evaluate(): Double = evaluationFunction.evaluate(this)

    abstract fun mutate(): Individual
    abstract fun crossover(partner: Individual): List<Individual>
}
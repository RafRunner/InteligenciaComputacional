package model

abstract class Individual {

    var fitness: Double? = null

    // Essa função deve preencher a variável fitness e a retornar
    abstract fun evaluate(): Double
    abstract fun mutate(): Individual
    abstract fun crossover(partners: List<Individual>): List<Individual>
}
package model

interface PopulationSelection {
    fun selectParents(population: Population): List<List<Individual>>
    fun cutDownPopulation(population: Population, children: List<Individual>): List<Individual>
}
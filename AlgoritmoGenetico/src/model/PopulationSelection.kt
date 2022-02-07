package model

interface PopulationSelection {
    fun selectParents(population: Population, newPopSize: Int): List<List<Individual>>
    fun cutDownPopulation(population: Population, children: List<Individual>): List<Individual>
}
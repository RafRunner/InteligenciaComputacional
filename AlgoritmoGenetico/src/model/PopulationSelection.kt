package model

interface PopulationSelection {
    fun selectParents(population: Population): List<List<Individual>>
    fun cutDownPopulation(parents: List<Individual>, children: List<Individual>): List<Individual>
}
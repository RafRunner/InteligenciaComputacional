package model

interface PopulationSelection {
    fun selectParents(population: Population): List<List<Individual>>
}
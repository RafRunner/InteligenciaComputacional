package model

class ElitistRouletteSelection : PopulationSelection {

    override fun selectParents(population: Population): List<List<Individual>> {
        return population.individuals.shuffled().chunked(2)
    }

    override fun cutDownPopulation(parents: List<Individual>, children: List<Individual>): List<Individual> {
        TODO("Not yet implemented")
    }
}
package model

import util.updated
import java.util.concurrent.ThreadLocalRandom

class ElitistRouletteSelection : PopulationSelection {

    override fun selectParents(population: Population, newPopSize: Int): List<List<Individual>> {
        val individuals = population.individuals

        val lowestFitness = individuals.minOf { it.fitness }
        val sumFunction = if (lowestFitness < 0) {
            { a: Double, b: Double -> a + b + lowestFitness }
        }
        else {
            { a: Double, b: Double -> a + b  }
        }


        val fitnessSum = individuals.fold(0.0) { acc, individual -> sumFunction(acc, individual.fitness) }

        val parents = mutableListOf<Individual>()

        while (parents.size < newPopSize) {
            val selectedNumber = ThreadLocalRandom.current().nextDouble(0.0, fitnessSum)
            var fitnessSoFar = 0.0
            parents.add(
                individuals.find { individual ->
                    fitnessSoFar = sumFunction(fitnessSoFar, individual.fitness)
                    return@find fitnessSoFar >= selectedNumber
                }!!
            )
        }

        return parents.chunked(2)
    }

    override fun cutDownPopulation(population: Population, children: List<Individual>): List<Individual> {
        val mostFitParent = population.mostFitIndividual()
        val droppedChild = ThreadLocalRandom.current().nextInt(0, children.size)

        return children.updated(droppedChild, mostFitParent)
    }
}
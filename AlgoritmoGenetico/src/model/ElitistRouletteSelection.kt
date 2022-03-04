package model

import util.updated
import java.util.concurrent.ThreadLocalRandom

class ElitistRouletteSelection : PopulationSelection {

    // Selecionamos os pais usando o algorítimo da roleta
    override fun selectParents(population: Population): List<List<Individual>> {
        val individuals = population.individuals

        // Esse é um tratamento para fitness negativa. Caso tenhamos fitness negativas, subtraímos a menor das fitness a todos
        // os indivíduos, fazendo com que a menor fitness seja 0
        val lowestFitness = individuals.minOf { it.fitness }
        val sum = if (lowestFitness < 0) {
            { a: Double, b: Double -> a + b - lowestFitness }
        }
        else {
            { a: Double, b: Double -> a + b  }
        }

        val fitnessSum = individuals.fold(0.0) { acc, individual -> sum(acc, individual.fitness) }

        val parents = mutableListOf<Individual>()

        // Os pais devem ser em mesmo número da população
        while (parents.size < individuals.size) {
            // Sorteamos um número entre 0 e a soma das fitness, então selecionamos o primeiro indivíduo cuja soma de
            // sua fitness + as fitness anteriores seja >= ao número sorteado para ser um dos pais (podem ter repetidos)
            val selectedNumber = ThreadLocalRandom.current().nextDouble(0.0, fitnessSum)
            var fitnessSoFar = 0.0
            parents.add(
                individuals.find { individual ->
                    fitnessSoFar = sum(fitnessSoFar, individual.fitness)
                    return@find fitnessSoFar >= selectedNumber
                }!!
            )
        }

        return parents.chunked(2)
    }

    // Aqui fazemos o elitismo. Matamos todos os pais menos o mais apto, o mais apto é inserido no lugar de um filho aleatório
    override fun cutDownPopulation(population: Population, children: List<Individual>): List<Individual> {
        val mostFitParent = population.mostFitIndividual()
        val droppedChild = ThreadLocalRandom.current().nextInt(0, children.size)

        return children.updated(droppedChild, mostFitParent)
    }
}
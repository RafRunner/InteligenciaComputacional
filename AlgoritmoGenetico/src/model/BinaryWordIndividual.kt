package model

import java.util.concurrent.ThreadLocalRandom

class BinaryWordIndividual(evaluationFunction: EvaluationFunction,
                           private val mutationChance: Double,
                           private val genes: List<List<Boolean>>) : Individual(evaluationFunction) {

    private val wordSize = genes.first().size

    override fun mutate(): Individual {
        val newGenes = mutableListOf<MutableList<Boolean>>()

        for (gene in genes) {
            val newGene = mutableListOf<Boolean>()
            for (bit in gene) {
                if (mutationChance > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                    newGene.add(!bit)
                }
                else {
                    newGene.add(bit)
                }
            }
            newGenes.add(newGene)
        }

        return createFromGenes(newGenes)
    }

    private fun cutGenes(cuttingPoint: Int): List<List<Boolean>> {
        val chromosome = genes.flatten()
        val partOne = chromosome.subList(0, cuttingPoint)
        val partTwo = chromosome.subList(cuttingPoint, chromosome.size)

        return listOf(partOne, partTwo)
    }

    private fun createFromGenes(genes: List<List<Boolean>>): BinaryWordIndividual {
        return BinaryWordIndividual(evaluationFunction, mutationChance, genes)
    }

    override fun crossover(partner: Individual): List<Individual> {
        if (partner !is BinaryWordIndividual) {
            throw RuntimeException("Partner must be a BinaryWordIndividual")
        }

        val cuttingPoint = ThreadLocalRandom.current().nextInt(1, wordSize - 1)
        val myParts = cutGenes(cuttingPoint)
        val partnerParts = partner.cutGenes(cuttingPoint)
        val child1Chromosome = myParts[0] + partnerParts[1]
        val child2Chromosome = partnerParts[0] + myParts[1]

        val child1Genes = child1Chromosome.chunked(wordSize)
        val child2Genes = child2Chromosome.chunked(wordSize)

        return listOf(createFromGenes(child1Genes), createFromGenes(child2Genes))
    }
}
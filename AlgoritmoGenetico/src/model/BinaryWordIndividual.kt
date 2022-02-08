package model

import java.util.concurrent.ThreadLocalRandom

open class BinaryWordIndividual(evaluationFunction: EvaluationFunction,
                                protected val crossOverChance: Double,
                                protected val mutationChance: Double,
                                val genes: List<String>) : Individual(evaluationFunction) {

    private val wordCount = genes.size
    private val wordSize = genes.first().length

    override fun mutate(): Individual {
        val newGenes = mutableListOf<String>()

        for (gene in genes) {
            val newGene = StringBuilder()
            for (bit in gene) {
                if (mutationChance > ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
                    newGene.append(if (bit == '0') '1' else '0')
                }
                else {
                    newGene.append(bit)
                }
            }
            newGenes.add(newGene.toString())
        }

        return createFromGenes(newGenes)
    }

    override fun crossover(partner: Individual): List<Individual> {
        if (partner !is BinaryWordIndividual) {
            throw RuntimeException("Partner must be a BinaryWordIndividual")
        }

        if (crossOverChance < ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
            return listOf(createFromGenes(genes), createFromGenes(partner.genes))
        }

        val cuttingPoint = ThreadLocalRandom.current().nextInt(1, wordSize)
        val myParts = cutGenes(cuttingPoint)
        val partnerParts = partner.cutGenes(cuttingPoint)
        val child1Chromosome = myParts[0] + partnerParts[1]
        val child2Chromosome = partnerParts[0] + myParts[1]

        val child1Genes = child1Chromosome.chunked(wordSize)
        val child2Genes = child2Chromosome.chunked(wordSize)

        return listOf(createFromGenes(child1Genes), createFromGenes(child2Genes))
    }

    override fun representation(): String {
        return (1 until wordCount).fold(genes[0]) { acc, i -> acc + " - " + genes[i] }
    }

    private fun cutGenes(cuttingPoint: Int): List<String> {
        val chromosome = genes.joinToString("")
        val partOne = chromosome.substring(0, cuttingPoint)
        val partTwo = chromosome.substring(cuttingPoint, chromosome.length)

        return listOf(partOne, partTwo)
    }

    open fun createFromGenes(genes: List<String>): BinaryWordIndividual {
        return BinaryWordIndividual(evaluationFunction, crossOverChance, mutationChance, genes)
    }
}
package model

import java.util.concurrent.ThreadLocalRandom

open class BinaryWordIndividual(evaluationFunction: EvaluationFunction,
                                protected val crossOverChance: Double,
                                protected val mutationChance: Double,
                                // As strings dos genes devem ser compostas por apenas 0 e 1
                                val genes: List<String>) : Individual(evaluationFunction) {

    private val wordCount = genes.size
    private val wordSize = genes.first().length

    init {
        // Decidi comentar essas verificações pois afetam muito a performance (indivíduos são criados com muita frequência)
//        if (crossOverChance < 0.0 || crossOverChance > 100.0) {
//            throw RuntimeException("crossOverChance must be between 0.0 and 100.0")
//        }
//        if (mutationChance < 0.0 || mutationChance > 100.0) {
//            throw RuntimeException("mutationChance must be between 0.0 and 100.0")
//        }
//        if (genes.any { it.contains(Regex("[^01]")) }) {
//            throw RuntimeException("genes must contain only 1s and 0s")
//        }
    }

    // Criamos novos genes percorrendo cada bit desse indivíduo e o invertendo caso um sorteio com chance de 'mutationChance'%
    // dê positivo e no fim retornamos um novo individuo
    override fun mutate(): Individual {
        val newGenes = mutableListOf<String>()

       genes.forEach { gene ->
            val newGene = StringBuilder()
            gene.forEach { bit ->
                if (mutationChance >= ThreadLocalRandom.current().nextDouble(0.0, 100.0)) {
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

    // Criamos dois novos indivíduos a partir de um par de pais, fazendo crossover com 1 ponto de corte aleatório, mas
    // 100 - 'crossOverChance'% das vezes apenas retornamos clones dos pais
    override fun crossover(partners: List<Individual>): List<Individual> {
        if (partners.size != 1) {
            throw RuntimeException("There must be a single partner")
        }
        val partner = partners.first()
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
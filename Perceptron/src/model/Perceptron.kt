package model

import java.util.concurrent.ThreadLocalRandom

class Perceptron(numberOfInputs: Int,
                 private val learningRate: Int,
                 private val activationFunction: (Int) -> Int,
                 randomWeights: Boolean = false) {

    var weights: List<Int>
    // Bias é uma constante igual a 1
    private val bias = 1

    init {
        // Os pesos começam como valores aleatórios entre -1 e 0 ou 0. O primeiro é o peso do bias
        weights = List(numberOfInputs + 1) { if (randomWeights) ThreadLocalRandom.current().nextInt(-1, 2) else 0 }
    }

    // Sempre retorna um valor entre 0 e 1
    fun calculateOutput(inputs: List<Int>): Int {
        val output = addBias(inputs).zip(weights).fold(0) { acc, inputAndWeight -> acc + inputAndWeight.first * inputAndWeight.second }
        return activationFunction(output)
    }

    fun train(inputsList: List<List<Int>>, expectedOutputs: List<Int>) {
        var fullyLearned = false
        var iteration = 0

        while (!fullyLearned) {
            iteration++
            println("Starting training iteration number $iteration\n")

            inputsList.indices.shuffled().forEach { index ->
                trainForInput(inputsList[index], expectedOutputs[index])
            }

            println("Final weights: $weights\n")

            fullyLearned = true

            inputsList.indices.forEach { index ->
                val input = inputsList[index]
                val expectedOutput = expectedOutputs[index]
                val error = calculateError(input, expectedOutput)
                val output = calculateOutput(input)

                println("Inputs $input gave error $error (actual: $output, expected: $expectedOutput)")
                if (error != 0) {
                    fullyLearned = false
                }
            }
            println("\n=================================================================\n")
        }

        println("The perceptron is fully learned after $iteration iteration(s)")
    }

    private fun calculateError(inputs: List<Int>, expectedValue: Int): Int {
        return expectedValue - calculateOutput(inputs)
    }

    private fun trainForInput(inputs: List<Int>, expectedValue: Int) {
        var error = calculateError(inputs, expectedValue)
        println("Training for input: $inputs and bias $bias expecting result $expectedValue")
        println("Initial weights: $weights, with output ${calculateOutput(inputs)} and error $error")

        if (error != 0) {
            calculateNewWeights(inputs, error)
            error = calculateError(inputs, expectedValue)
            println("New weights: $weights, with output ${calculateOutput(inputs)} and error $error")
        }

        println("-----------------------------------------------------------------\n")
    }

    private fun addBias(inputs: List<Int>): List<Int> = listOf(bias) + inputs

    private fun calculateNewWeights(inputs: List<Int>, error: Int) {
        weights = addBias(inputs).zip(weights).map { inputAndWeight -> inputAndWeight.second + (error * learningRate * inputAndWeight.first) }
    }
}
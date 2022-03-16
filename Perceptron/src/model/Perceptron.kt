package model

import java.lang.Integer.max
import java.lang.Integer.min

class Perceptron(numberOfInputs: Int, val learningRate: Int) {

    var weights: List<Int>
    // Bias é uma constante igual a 1
    private val bias = 1

    init {
        // Os pesos começam como 0. O primeiro é o peso do bias
        weights = List(numberOfInputs + 1) { 0 }
    }

    // Sempre retorna um valor entre 0 e 1
    fun calculateOutput(inputs: List<Int>): Int {
        return max(0, min(1, addBias(inputs).zip(weights).fold(0) { acc, pair -> acc + pair.first * pair.second }))
    }

    fun calculateError(inputs: List<Int>, expectedValue: Int): Int {
        return expectedValue - calculateOutput(inputs)
    }

    fun train(inputs: List<Int>, expectedValue: Int) {
        var error = calculateError(inputs, expectedValue)
        println("Training for input: $inputs and bias $bias expecting result $expectedValue")
        println("Initial weights: $weights, with output ${calculateOutput(inputs)} and error $error")

        while (error != 0) {
            calculateNewWeights(inputs, error)
            error = calculateError(inputs, expectedValue)
            println("New weights: $weights, with output ${calculateOutput(inputs)} and error $error")
        }

        println("-----------------------------------------------------------------\n")
    }

    private fun addBias(inputs: List<Int>): List<Int> = listOf(bias) + inputs

    private fun calculateNewWeights(inputs: List<Int>, error: Int) {
        weights = addBias(inputs).zip(weights).map { pair -> pair.second + (error * learningRate * pair.first) }
    }
}
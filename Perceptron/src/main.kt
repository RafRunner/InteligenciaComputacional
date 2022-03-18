import model.Perceptron
import model.stepActivationFunction

fun main() {
//    val inputs = listOf(listOf(0, 0), listOf(0, 1), listOf(1, 0), listOf(1, 1))
//    val expectedOutputs = listOf(0, 0, 1, 1)

    val inputs = listOf(listOf(1, 1), listOf(0, 1), listOf(1, 0), listOf(0, 0))
    val expectedOutputs = listOf(1, 0, 1, 0)

    val perceptron = Perceptron(2, 1, ::stepActivationFunction)

    perceptron.train(inputs, expectedOutputs)
}


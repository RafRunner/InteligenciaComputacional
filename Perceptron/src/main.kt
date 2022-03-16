import model.Perceptron

fun main() {
    val inputs = listOf(listOf(0, 0), listOf(0, 1), listOf(1, 0), listOf(1, 1))
    val expectedOutputs = listOf(0, 0, 1, 1)

//    val inputs = listOf(listOf(1, 1), listOf(0, 1), listOf(1, 0), listOf(0, 0))
//    val expectedOutputs = listOf(1, 0, 1, 0)

    val perceptron = Perceptron(2, 1)

    var fullyLearned = false
    var iteration = 0

    while (!fullyLearned) {
        iteration++
        println("Starting training iteration number $iteration\n")

        inputs.indices.shuffled().forEach { index ->
            perceptron.train(inputs[index], expectedOutputs[index])
        }

        println("Final weights: ${perceptron.weights}\n")

        fullyLearned = true

        inputs.indices.forEach { index ->
            val input = inputs[index]
            val expectedOutput = expectedOutputs[index]
            val error = perceptron.calculateError(input, expectedOutput)
            val output = perceptron.calculateOutput(input)

            println("Inputs $input gave error $error (expected: $expectedOutput, actual: $output)")
            if (error != 0) {
                fullyLearned = false
            }
        }
        println("\n=================================================================\n")
    }

    println("The perceptron is fully learned after $iteration iteration(s)")
}


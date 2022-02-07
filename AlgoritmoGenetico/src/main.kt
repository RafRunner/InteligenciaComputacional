import F6.F6EvaluationFunction
import model.*

fun main() {
    val population = Population(
        100,
        RandomBinaryInitializer(
            F6EvaluationFunction(),
            65.0,
            0.8,
            2,
            22
        ),
        ElitistRouletteSelection(),
        GenerationStopCondition(40)
    )

    population.train()
}

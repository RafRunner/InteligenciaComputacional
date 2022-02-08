import F6.RandomF6Initializer
import model.*

fun main() {
    val population = Population(
        100,
        RandomF6Initializer(
            65.0,
            0.8,
        ),
        ElitistRouletteSelection(),
        GenerationStopCondition(40)
    )

    population.train()
}

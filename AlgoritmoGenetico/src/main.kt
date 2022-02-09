import F6.RandomF6Initializer
import model.*
import kotlin.system.measureTimeMillis

fun main() {
    val population = Population(
        100,
        RandomF6Initializer(
            65.0,
            0.8,
        ),
        ElitistRouletteSelection(),
        GenerationStopCondition(100),
        5
    )

    println("Training time: " + measureTimeMillis {
        population.train()
    } + "ms")
}

import F6.RandomF6Initializer
import model.ElitistRouletteSelection
import model.GenerationStopCondition
import model.Population
import view.createAndShowGraph
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
        8
    )

    println("Training time: " + measureTimeMillis {
        population.train()
    } + "ms")

    createAndShowGraph("Best fitness", population.populationHistory.map { it.mostFitIndividual.fitness })
    createAndShowGraph("Average fitness", population.populationHistory.map { it.fitnessAverage })
}

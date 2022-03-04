import F6.RandomF6Initializer
import model.ElitistRouletteSelection
import model.GenerationStopCondition
import model.Population
import view.createAndShowGraph
import java.awt.Point
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

    val frame1 = createAndShowGraph("Best fitness", population.populationHistory.map { it.mostFitIndividual.fitness }, Point(50, 100))
    createAndShowGraph("Average fitness", population.populationHistory.map { it.fitnessAverage }, Point(frame1.location.x + frame1.width + 10, frame1.location.y))
}

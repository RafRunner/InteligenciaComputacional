package model

interface EvaluationFunction {
    fun evaluate(individual: Individual): Double
}
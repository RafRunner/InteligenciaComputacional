package model

interface StopCondition {
    fun shouldStop(population: Population): Boolean
}
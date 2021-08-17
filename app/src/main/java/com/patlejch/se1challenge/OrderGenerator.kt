package com.patlejch.se1challenge

import kotlin.random.Random
import java.util.UUID

interface OrderGenerator {
    fun next() : Order
}

class RandomOrderGenerator(_probability: Double): OrderGenerator {
    private var last: Order
    private val probability: Double
    
    init {
        last = createNewOrder()
        probability = _probability
    }
    
    override fun next() : Order {
        if (shouldCreateNewOrder()) {
            last = createNewOrder()
        }
        return last
    }

    private fun createNewOrder() = Order(UUID.randomUUID().toString())
    private fun shouldCreateNewOrder() = getRandomProbability() < probability
    private fun getRandomProbability() = Random.nextDouble(0.0, 1.0)
}

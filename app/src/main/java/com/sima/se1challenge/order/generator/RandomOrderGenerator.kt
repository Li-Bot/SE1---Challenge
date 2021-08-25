package com.sima.se1challenge.order.generator

import com.sima.se1challenge.model.Order
import java.util.*
import kotlin.random.Random

class RandomOrderGenerator(_probability: Double) : OrderGenerator {
    private var last: Order
    private val probability: Double

    init {
        last = createNewOrder()
        probability = _probability
    }

    override fun next(): Order {
        if (shouldCreateNewOrder()) {
            last = createNewOrder()
        }
        return last
    }

    private fun createNewOrder() = Order(UUID.randomUUID().toString())
    private fun shouldCreateNewOrder() = getRandomProbability() < probability
    private fun getRandomProbability() = Random.nextDouble(0.0, 1.0)
}

package com.patlejch.se1challenge

import kotlin.random.Random

interface OrderPricingLoader {
    fun load(order: Order, completion: (result: Result<OrderPrice>) -> Unit)
}

class OrderPricingLoaderException(message: String): Exception(message)

class RandomOrderPricingLoader : OrderPricingLoader {
    override fun load(order: Order, completion: (result: Result<OrderPrice>) -> Unit) {
        performOnBackground {
            if (isFailure()) {
                completion(Result.failure(getPricingException()))
            } else {
                completion(Result.success(getRandomOrderPrice()))
            }
        }
    }

    private fun performOnBackground(action: Runnable) = DispatchQueue.execute(action)
    private fun isFailure() = Random.nextBoolean()
    private fun getPricingException() = OrderPricingLoaderException(":(")
    private fun getRandomOrderPrice() = OrderPrice(getRandomPrice())
    private fun getRandomPrice() = Random.nextDouble(1000.0, 200000.0)
}

package com.sima.se1challenge.order.loader

import com.sima.se1challenge.model.Order
import com.sima.se1challenge.model.OrderPrice
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class OrderPricingLoaderException(message: String) : Exception(message)

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

object DispatchQueue {
    private val pool = Executors.newScheduledThreadPool(4)

    fun execute(runnable: Runnable) = pool.execute(runnable)
    fun shutdown() {
        pool.awaitTermination(1, TimeUnit.SECONDS)
        pool.shutdown()
    }
}

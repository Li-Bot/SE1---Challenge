package com.sima.se1challenge.order.loader

import com.sima.se1challenge.model.Order
import com.sima.se1challenge.model.OrderPrice

class RepetitionOrderPricingLoader(
    private val orderPricingLoader: OrderPricingLoader
) {
    fun load(order: Order, repetitions: Int = 1, completion: (result: Result<OrderPrice>) -> Unit) {
        orderPricingLoader.load(order) {
            if (it.isFailure && repetitions > 0) {
                load(order, repetitions - 1, completion)
            } else {
                completion(it)
            }
        }
    }
}

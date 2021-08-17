package com.patlejch.se1challenge.uc

import com.patlejch.se1challenge.MultiplierLoader
import com.patlejch.se1challenge.OrderPrice

class ApplyMultiplierUC(
    private val multiplierLoader: MultiplierLoader
): UseCase<OrderPrice, OrderPrice> {

    override fun invoke(input: OrderPrice, completion: (Result<OrderPrice>) -> Unit) {
        multiplierLoader.loadMultiplier {
            withResult(it, completion) {
                val result = Result.success(OrderPrice(it.value * input.price))
                completion(result)
            }
        }
    }
}

package com.patlejch.se1challenge.uc

import com.patlejch.se1challenge.Order
import com.patlejch.se1challenge.OrderPrice
import com.patlejch.se1challenge.OrderPricingLoader

class GetBasePriceUC(
    private val orderPricingLoader: OrderPricingLoader
): UseCase<GetBasePriceUC.In, OrderPrice> {

    override fun invoke(input: In, completion: (Result<OrderPrice>) -> Unit) {
        orderPricingLoader.load(input.order) { result ->
            result.onSuccess {
                completion(result)
            }.onFailure {
                if (input.attempts > 1) {
                    this(input.copy(attempts = input.attempts - 1), completion)
                } else {
                    completion(result)
                }
            }
        }
    }

    data class In(
        val order: Order,
        val attempts: Int = 2
    )
}

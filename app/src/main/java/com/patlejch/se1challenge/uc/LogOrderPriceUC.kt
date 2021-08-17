package com.patlejch.se1challenge.uc

import com.patlejch.se1challenge.Order
import com.patlejch.se1challenge.OrderPrice
import com.patlejch.se1challenge.logging.Logger

class LogOrderPriceUC(
    private val logger: Logger
) : UseCase<LogOrderPriceUC.In, Unit> {

    override fun invoke(input: In, completion: (Result<Unit>) -> Unit) {
        logger.log("Order: ${input.order.id}. Price: ${input.orderPrice.price}")
        completion(Result.success(Unit))
    }

    data class In(
        val order: Order,
        val orderPrice: OrderPrice
    )
}

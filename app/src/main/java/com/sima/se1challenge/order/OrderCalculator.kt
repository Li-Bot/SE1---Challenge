package com.sima.se1challenge.order

import com.sima.se1challenge.model.Order
import com.sima.se1challenge.model.OrderPrice
import com.sima.se1challenge.order.cache.OrderCache
import com.sima.se1challenge.order.discount.OrderPriceDiscount
import com.sima.se1challenge.order.generator.OrderGenerator
import com.sima.se1challenge.order.loader.RepetitionOrderPricingLoader
import com.sima.se1challenge.order.logging.OrderLogger
import com.sima.se1challenge.order.multiplier.OrderPriceMultiplier

class OrderCalculator(
    private val orderGenerator: OrderGenerator,
    private val repetitionOrderPricingLoader: RepetitionOrderPricingLoader,
    private val orderCache: OrderCache,
    private val multiplier: OrderPriceMultiplier,
    private val discount: OrderPriceDiscount,
    private val orderLogger: OrderLogger
) {
    fun calculate(completion: (Result<OrderPrice>) -> Unit) {
        val order = orderGenerator.next()
        orderCache.getOrderPrice(order)?.let {
            completion(Result.success(it))
            return@calculate
        }

        repetitionOrderPricingLoader.load(order) {
            val result = it.map { price ->
                price.applyAction(order, multiplier)
                    .applyAction(order, discount)
            }

            result.onSuccess {
                orderCache.putOrderPrice(order, it)
            }

            completion(result)
        }
    }

    private fun OrderPrice.applyAction(
        order: Order,
        orderPriceAction: OrderPriceAction,
        logging: Boolean = true
    ): OrderPrice {
        fun logOrder(orderPrice: OrderPrice) {
            if (logging) orderLogger.log(order, orderPrice)
        }

        logOrder(this)
        return orderPriceAction.apply(this).also {
            logOrder(it)
        }
    }
}

package com.sima.se1challenge.order.multiplier

import com.sima.se1challenge.model.OrderPrice
import com.sima.se1challenge.model.PriceMultiplier
import com.sima.se1challenge.order.OrderPriceAction

interface OrderPriceMultiplier : OrderPriceAction {
    fun calculateMultiplier(orderPrice: OrderPrice): PriceMultiplier

    override fun apply(orderPrice: OrderPrice): OrderPrice {
        val multiplier = calculateMultiplier(orderPrice)
        return OrderPrice(orderPrice.price * multiplier.value)
    }

    companion object {
        fun getDefault(): OrderPriceMultiplier = OrderPriceMultiplierImpl()
    }
}

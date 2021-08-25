package com.sima.se1challenge.order.multiplier

import com.sima.se1challenge.model.OrderPrice
import com.sima.se1challenge.model.PriceMultiplier

class OrderPriceMultiplierImpl : OrderPriceMultiplier {
    override fun calculateMultiplier(orderPrice: OrderPrice): PriceMultiplier {
        return PriceMultiplier(1.2)
    }
}

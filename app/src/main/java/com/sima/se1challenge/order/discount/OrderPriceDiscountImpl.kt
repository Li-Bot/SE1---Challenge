package com.sima.se1challenge.order.discount

import com.sima.se1challenge.model.OrderPrice
import com.sima.se1challenge.model.PriceDiscount

class OrderPriceDiscountImpl : OrderPriceDiscount {
    override fun calculateDiscount(orderPrice: OrderPrice): PriceDiscount {
        return when {
            orderPrice.price >= 150000.0 -> PriceDiscount(0.5)
            orderPrice.price >= 100000.0 -> PriceDiscount(0.3)
            else -> PriceDiscount(0.0)
        }
    }
}

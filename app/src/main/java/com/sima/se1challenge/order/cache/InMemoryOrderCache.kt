package com.sima.se1challenge.order.cache

import com.sima.se1challenge.model.Order
import com.sima.se1challenge.model.OrderPrice

class InMemoryOrderCache : OrderCache {
    private val cache = mutableMapOf<String, OrderPrice>()

    override fun putOrderPrice(order: Order, orderPrice: OrderPrice) {
        cache[order.id] = orderPrice
    }

    override fun getOrderPrice(order: Order): OrderPrice? {
        return cache[order.id]
    }
}

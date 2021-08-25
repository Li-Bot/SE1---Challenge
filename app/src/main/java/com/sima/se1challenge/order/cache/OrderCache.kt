package com.sima.se1challenge.order.cache

import com.sima.se1challenge.model.Order
import com.sima.se1challenge.model.OrderPrice

interface OrderCache {
    fun putOrderPrice(order: Order, orderPrice: OrderPrice)

    fun getOrderPrice(order: Order): OrderPrice?

    companion object {
        fun getDefault(): OrderCache = InMemoryOrderCache()
    }
}

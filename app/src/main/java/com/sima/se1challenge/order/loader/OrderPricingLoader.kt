package com.sima.se1challenge.order.loader

import com.sima.se1challenge.model.Order
import com.sima.se1challenge.model.OrderPrice

interface OrderPricingLoader {
    fun load(order: Order, completion: (result: Result<OrderPrice>) -> Unit)
}

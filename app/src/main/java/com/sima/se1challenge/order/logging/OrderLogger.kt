package com.sima.se1challenge.order.logging

import com.sima.se1challenge.model.Order
import com.sima.se1challenge.model.OrderPrice

interface OrderLogger {
    fun log(order: Order, orderPrice: OrderPrice)

    companion object {
        fun getDefault(): OrderLogger = OrderLoggerImpl()
    }
}

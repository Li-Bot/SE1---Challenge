package com.sima.se1challenge.order.logging

import android.util.Log
import com.sima.se1challenge.model.Order
import com.sima.se1challenge.model.OrderPrice

class OrderLoggerImpl : OrderLogger {
    override fun log(order: Order, orderPrice: OrderPrice) {
        Log.i("Order", "Order: ${order.id} Price: ${orderPrice.price}")
    }
}

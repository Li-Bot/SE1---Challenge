package com.sima.se1challenge.order

import com.sima.se1challenge.model.OrderPrice

interface OrderPriceAction {
    fun apply(orderPrice: OrderPrice): OrderPrice
}

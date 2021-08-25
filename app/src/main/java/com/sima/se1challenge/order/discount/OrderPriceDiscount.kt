package com.sima.se1challenge.order.discount

import com.sima.se1challenge.model.OrderPrice
import com.sima.se1challenge.model.PriceDiscount
import com.sima.se1challenge.order.OrderPriceAction

interface OrderPriceDiscount : OrderPriceAction {
    fun calculateDiscount(orderPrice: OrderPrice): PriceDiscount

    override fun apply(orderPrice: OrderPrice): OrderPrice {
        val discount = calculateDiscount(orderPrice)
        return OrderPrice(orderPrice.price - orderPrice.price * discount.value)
    }

    companion object {
        fun getDefault(): OrderPriceDiscount = OrderPriceDiscountImpl()
    }
}

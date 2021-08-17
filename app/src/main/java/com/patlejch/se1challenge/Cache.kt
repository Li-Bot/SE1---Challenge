package com.patlejch.se1challenge

interface Cache {
    fun findPrice(order: Order, completion: (Result<OrderPrice?>) -> Unit)
    fun savePrice(order: Order, orderPrice: OrderPrice, completion: (Result<Unit>) -> Unit)
}

class InMemoryCache : Cache {

    private val prices = hashMapOf<Order, OrderPrice>()

    override fun findPrice(order: Order, completion: (Result<OrderPrice?>) -> Unit) {
        completion(Result.success(prices[order]))
    }

    override fun savePrice(
        order: Order,
        orderPrice: OrderPrice,
        completion: (Result<Unit>) -> Unit
    ) {
        prices[order] = orderPrice
        completion(Result.success(Unit))
    }
}

package com.patlejch.se1challenge.uc

import com.patlejch.se1challenge.Order
import com.patlejch.se1challenge.OrderGenerator
import com.patlejch.se1challenge.OrderPrice

class GetOrderWithPriceUC(
    private val orderGenerator: OrderGenerator,
    private val getOrderPriceUC: UseCase<Order, OrderPrice>
): UseCase<Unit, GetOrderWithPriceUC.Out> {

    override fun invoke(input: Unit, completion: (Result<Out>) -> Unit) {
        val order = orderGenerator.next()
        getOrderPriceUC(order) {
            withResult(it, completion) {
                completion(Result.success(Out(order, it)))
            }
        }
    }

    data class Out(
        val order: Order,
        val orderPrice: OrderPrice
    )
}

package com.patlejch.se1challenge.uc

import com.patlejch.se1challenge.Cache
import com.patlejch.se1challenge.Order
import com.patlejch.se1challenge.OrderPrice

class GetOrderPriceUC(
    private val cache: Cache,
    private val calculateOrderPriceUC: UseCase<Order, OrderPrice>
) : UseCase<Order, OrderPrice> {

    override fun invoke(input: Order, completion: (Result<OrderPrice>) -> Unit) {
        cache.findPrice(input) { result ->
            result.onSuccess {
                handleCacheSuccess(input, it, completion)
            }.onFailure {
                completion(Result.failure(it))
            }
        }
    }

    private fun handleCacheSuccess(
        order: Order,
        result: OrderPrice?,
        completion: (Result<OrderPrice>) -> Unit
    ) {
        result?.let {
            completion(Result.success(it))
            return
        }

        calculateOrderPriceUC(order) {
            withResult(it, completion) { price ->
                cache.savePrice(order, price) {
                    completion(Result.success(price))
                }
            }
        }
    }
}

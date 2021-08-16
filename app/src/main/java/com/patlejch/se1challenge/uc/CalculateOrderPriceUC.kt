package com.patlejch.se1challenge.uc

import com.patlejch.se1challenge.Order
import com.patlejch.se1challenge.OrderPrice

class CalculateOrderPriceUC(
    private val getBasePriceUC: UseCase<GetBasePriceUC.In, OrderPrice>,
    private val applyMultiplierUC: UseCase<OrderPrice, OrderPrice>,
    private val applyDiscountUC: UseCase<OrderPrice, OrderPrice>,
    private val logOrderPriceUC: UseCase<LogOrderPriceUC.In, Unit>
) : UseCase<Order, OrderPrice> {

    override fun invoke(input: Order, completion: (Result<OrderPrice>) -> Unit) =
        getBasePriceUC(GetBasePriceUC.In(input)) {
            withResult(it, completion) {
                handleBasePrice(input, it, completion)
            }
        }

    private fun handleBasePrice(
        order: Order,
        basePrice: OrderPrice,
        completion: (Result<OrderPrice>) -> Unit
    ) {
        logOrderWithPrice(order, basePrice)
        applyMultiplierUC(basePrice) {
            withResult(it, completion) {
                logOrderWithPrice(order, it)
                handleMultipliedPrice(order, it, completion)
            }
        }
    }

    private fun handleMultipliedPrice(
        order: Order,
        multipliedPrice: OrderPrice,
        completion: (Result<OrderPrice>) -> Unit
    ) {
        logOrderWithPrice(order, multipliedPrice)
        applyDiscountUC(multipliedPrice) {
            withResult(it, completion) {
                logOrderWithPrice(order, it)
                completion(Result.success(it))
            }
        }
    }

    private fun logOrderWithPrice(order: Order, orderPrice: OrderPrice) {
        logOrderPriceUC(LogOrderPriceUC.In(order, orderPrice)) {}
    }
}

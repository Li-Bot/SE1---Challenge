package com.patlejch.se1challenge.uc

import com.patlejch.se1challenge.OrderPrice
import com.patlejch.se1challenge.PriceBasedDiscountStore

class ApplyPriceBasedDiscountUC(
    private val priceBasedDiscountStore: PriceBasedDiscountStore
) : UseCase<OrderPrice, OrderPrice> {

    override fun invoke(input: OrderPrice, completion: (Result<OrderPrice>) -> Unit) {
        priceBasedDiscountStore.getPriceBasedDiscounts {
            withResult(it, completion) {
                val discountRate = it.filter {
                    it.minPrice.price <= input.price
                }.maxByOrNull {
                    it.discount.value
                }?.discount?.value

                val originalPrice = input.price
                val discountedPrice = if (discountRate == null)
                    originalPrice
                else
                    originalPrice * (1.0 - (discountRate / 100.0))

                completion(Result.success(OrderPrice(discountedPrice)))
            }
        }
    }
}

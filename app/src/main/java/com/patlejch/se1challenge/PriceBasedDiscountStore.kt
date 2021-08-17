package com.patlejch.se1challenge

interface PriceBasedDiscountStore {
    fun getPriceBasedDiscounts(completion: (Result<List<PriceBasedDiscount>>) -> Unit)
}

class InMemoryPriceBasedDiscountStore : PriceBasedDiscountStore {

    private val pricedBasedDiscounts = listOf(
        PriceBasedDiscount(OrderPrice(100000.0), PriceDiscount(30.0)),
        PriceBasedDiscount(OrderPrice(150000.0), PriceDiscount(50.0))
    )

    override fun getPriceBasedDiscounts(completion: (Result<List<PriceBasedDiscount>>) -> Unit) =
        completion(Result.success(pricedBasedDiscounts))
}

data class PriceBasedDiscount(
    val minPrice: OrderPrice,
    val discount: PriceDiscount
)

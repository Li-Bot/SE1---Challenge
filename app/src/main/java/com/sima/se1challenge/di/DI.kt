package com.sima.se1challenge.di

import com.sima.se1challenge.SEChallengeViewModel
import com.sima.se1challenge.order.OrderCalculator
import com.sima.se1challenge.order.cache.OrderCache
import com.sima.se1challenge.order.discount.OrderPriceDiscount
import com.sima.se1challenge.order.generator.OrderGenerator
import com.sima.se1challenge.order.loader.OrderPricingLoader
import com.sima.se1challenge.order.loader.RepetitionOrderPricingLoader
import com.sima.se1challenge.order.logging.OrderLogger
import com.sima.se1challenge.order.multiplier.OrderPriceMultiplier

object DI {
    fun orderCache() = OrderCache.getDefault()

    fun orderPriceDiscount() = OrderPriceDiscount.getDefault()

    fun orderGenerator() = OrderGenerator.getDefault()

    fun orderPricingLoader() = OrderPricingLoader.getDefault()

    fun orderLogger() = OrderLogger.getDefault()

    fun orderPriceMultiplier() = OrderPriceMultiplier.getDefault()

    fun repetitionOrderPricingLoader() = RepetitionOrderPricingLoader(orderPricingLoader())

    fun orderCalculator() = OrderCalculator(
        orderGenerator(),
        repetitionOrderPricingLoader(),
        orderCache(),
        orderPriceMultiplier(),
        orderPriceDiscount(),
        orderLogger()
    )

    fun seChallengeViewModel() = SEChallengeViewModel(
        orderCalculator()
    )
}

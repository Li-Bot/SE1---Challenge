package com.patlejch.se1challenge.di

import com.patlejch.se1challenge.*
import com.patlejch.se1challenge.logging.AndroidLogger
import com.patlejch.se1challenge.logging.Logger
import com.patlejch.se1challenge.uc.*

fun cache(): Cache = InMemoryCache()

fun orderGenerator(): OrderGenerator = RandomOrderGenerator(0.8)

fun orderPricingLoader(): OrderPricingLoader = RandomOrderPricingLoader()

fun multiplierLoader(): MultiplierLoader = ConstantMultiplierLoader(2.0)

fun priceBasedDiscountStore(): PriceBasedDiscountStore = InMemoryPriceBasedDiscountStore()

fun logger(): Logger = AndroidLogger()

fun logOrderPriceUC(): UseCase<LogOrderPriceUC.In, Unit> = LogOrderPriceUC(logger())

fun getBasePriceUC(): UseCase<GetBasePriceUC.In, OrderPrice> =
    GetBasePriceUC(orderPricingLoader())

fun applyMultiplierUC(): UseCase<OrderPrice, OrderPrice> = ApplyMultiplierUC(multiplierLoader())

fun applyPriceBasedDiscountUC(): UseCase<OrderPrice, OrderPrice> =
    ApplyPriceBasedDiscountUC(priceBasedDiscountStore())

fun calculateOrderPriceUC(): UseCase<Order, OrderPrice> = CalculateOrderPriceUC(
    getBasePriceUC(),
    applyMultiplierUC(),
    applyPriceBasedDiscountUC(),
    logOrderPriceUC()
)

fun getOrderPriceUC(): UseCase<Order, OrderPrice> = GetOrderPriceUC(
    cache(),
    calculateOrderPriceUC()
)

fun getOrderWithPriceUC(): UseCase<Unit, GetOrderWithPriceUC.Out> = GetOrderWithPriceUC(
    orderGenerator(),
    getOrderPriceUC()
)

fun seChallengeViewModel(): SEChallengeViewModel = SEChallengeViewModel(getOrderWithPriceUC())

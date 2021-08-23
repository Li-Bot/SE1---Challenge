package wiki.depasquale.challenge.di

import wiki.depasquale.challenge.core.PriceCalculator
import wiki.depasquale.challenge.core.PriceCalculatorOptions
import wiki.depasquale.challenge.core.PriceDecorator
import wiki.depasquale.challenge.core.discount.DefaultPriceDiscountLoader
import wiki.depasquale.challenge.core.discount.PriceDiscountLoader
import wiki.depasquale.challenge.core.log.DefaultOrderLogger
import wiki.depasquale.challenge.core.log.OrderLogger
import wiki.depasquale.challenge.core.multiplier.DefaultPriceMultiplierLoader
import wiki.depasquale.challenge.core.multiplier.PriceMultiplierLoader
import wiki.depasquale.challenge.core.order.OrderGenerator
import wiki.depasquale.challenge.core.order.RandomOrderGenerator
import wiki.depasquale.challenge.core.pricing.OrderPricingLoader
import wiki.depasquale.challenge.core.pricing.RandomOrderPricingLoader
import wiki.depasquale.challenge.persistence.InMemoryPersistableOrderPrice
import wiki.depasquale.challenge.persistence.PersistableOrderPrice

object ServiceLocator {

    private lateinit var orderLogger: OrderLogger

    private fun getLogger(): OrderLogger {
        if (this::orderLogger.isInitialized) {
            return orderLogger
        }

        synchronized(this) {
            if (this::orderLogger.isInitialized) {
                return orderLogger
            }

            orderLogger = DefaultOrderLogger()
        }

        return orderLogger
    }

    private fun getMultiplierLoader(): PriceMultiplierLoader {
        return DefaultPriceMultiplierLoader()
    }

    private fun getDiscountLoader(): PriceDiscountLoader {
        return DefaultPriceDiscountLoader()
    }

    private fun getPriceDecorator(): PriceDecorator {
        return PriceDecorator.Builder()
            .setMultiplierLoader(getMultiplierLoader())
            .setDiscountLoader(getDiscountLoader())
            .setLogger(getLogger())
            .build()
    }

    private fun getPersistableOrderPrice(): PersistableOrderPrice {
        return InMemoryPersistableOrderPrice()
    }

    private fun getPricingLoader(): OrderPricingLoader {
        return RandomOrderPricingLoader()
    }

    fun getPriceCalculator(
        options: PriceCalculatorOptions = PriceCalculatorOptions.default
    ): PriceCalculator {
        return PriceCalculator.Builder(options)
            .setDecorator(getPriceDecorator())
            .setPersistableOrderPrice(getPersistableOrderPrice())
            .setPricingLoader(getPricingLoader())
            .build()
    }

    fun getOrderGenerator(): OrderGenerator =
        RandomOrderGenerator(probability = 0.5)

}
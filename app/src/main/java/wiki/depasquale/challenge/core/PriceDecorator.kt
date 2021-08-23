package wiki.depasquale.challenge.core

import wiki.depasquale.challenge.core.discount.PriceDiscountLoader
import wiki.depasquale.challenge.core.log.OrderLogger
import wiki.depasquale.challenge.core.multiplier.PriceMultiplierLoader
import wiki.depasquale.challenge.model.ResultOrder

typealias OnPriceDecoratedListener = (ResultOrder) -> Unit

class PriceDecorator private constructor(
    private val discountLoader: PriceDiscountLoader,
    private val multiplierLoader: PriceMultiplierLoader,
    private val logger: OrderLogger
) {

    fun decorate(order: ResultOrder, listener: OnPriceDecoratedListener) {
        fun chainDiscount(order: ResultOrder) =
            decorateWithDiscount(order, listener)

        fun chainMultiplier(order: ResultOrder) =
            decorateWithMultiplier(order) { chainDiscount(it) }

        chainMultiplier(order)
    }

    private fun decorateWithMultiplier(
        order: ResultOrder,
        listener: OnPriceDecoratedListener
    ) = multiplierLoader.getPriceMultiplier(order) {
        logger.log(order)
        val result = order.updatePrice { original ->
            original * it.value
        }
        logger.log(result)
        listener(result)
    }

    private fun decorateWithDiscount(
        order: ResultOrder,
        listener: OnPriceDecoratedListener
    ) = discountLoader.getPriceDiscount(order) {
        logger.log(order)
        val result = order.updatePrice { original ->
            original * it.value
        }
        logger.log(result)
        listener(result)
    }

    // ---

    class Builder {

        private lateinit var discountLoader: PriceDiscountLoader
        private lateinit var multiplierLoader: PriceMultiplierLoader
        private lateinit var logger: OrderLogger

        fun setDiscountLoader(loader: PriceDiscountLoader) = apply {
            this.discountLoader = loader
        }

        fun setMultiplierLoader(loader: PriceMultiplierLoader) = apply {
            this.multiplierLoader = loader
        }

        fun setLogger(logger: OrderLogger) = apply {
            this.logger = logger
        }

        fun build() = PriceDecorator(
            discountLoader = discountLoader,
            multiplierLoader = multiplierLoader,
            logger = logger
        )
    }

}
package wiki.depasquale.challenge.core

import wiki.depasquale.challenge.core.pricing.OrderPricingLoader
import wiki.depasquale.challenge.core.pricing.RepeatingOrderPricingLoader
import wiki.depasquale.challenge.model.Order
import wiki.depasquale.challenge.model.ResultOrder
import wiki.depasquale.challenge.persistence.PersistableOrderPrice

typealias OnPriceResultListener = (Result<ResultOrder>) -> Unit

class PriceCalculator private constructor(
    private val pricingLoader: OrderPricingLoader,
    private val priceDecorator: PriceDecorator,
    private val orderPrice: PersistableOrderPrice
) {

    fun getPrice(order: Order, listener: OnPriceResultListener) {
        val cachedPrice = orderPrice.getOrNull(order)
        if (cachedPrice != null) {
            val result = ResultOrder(
                order = order,
                price = cachedPrice
            )
            return listener(Result.success(result))
        }

        fun saveAndDispatch(order: ResultOrder) {
            orderPrice.put(order.order, order.price)
            listener(Result.success(order))
        }

        fun decorate(order: ResultOrder) = priceDecorator.decorate(order) {
            saveAndDispatch(it)
        }

        pricingLoader.load(order) {
            val exception = it.exceptionOrNull()
            if (exception != null) {
                return@load listener(it as Result<ResultOrder>)
            }

            val result = ResultOrder(order, it.getOrThrow())
            decorate(result)
        }
    }

    class Builder(private val options: PriceCalculatorOptions) {

        private lateinit var pricingLoader: OrderPricingLoader
        private lateinit var priceDecorator: PriceDecorator
        private lateinit var orderPrice: PersistableOrderPrice

        fun setPricingLoader(loader: OrderPricingLoader) = apply {
            this.pricingLoader = loader
        }

        fun setDecorator(decorator: PriceDecorator) = apply {
            this.priceDecorator = decorator
        }

        fun setPersistableOrderPrice(orderPrice: PersistableOrderPrice) = apply {
            this.orderPrice = orderPrice
        }

        fun build(): PriceCalculator {
            var loader = pricingLoader

            if (options.repeatAtMost > 0) {
                loader = RepeatingOrderPricingLoader.from(loader, options.repeatAtMost)
            }

            return PriceCalculator(
                pricingLoader = loader,
                priceDecorator = priceDecorator,
                orderPrice = orderPrice
            )
        }

    }

}
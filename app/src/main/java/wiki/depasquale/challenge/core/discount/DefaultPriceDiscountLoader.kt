package wiki.depasquale.challenge.core.discount

import wiki.depasquale.challenge.model.PriceDiscount
import wiki.depasquale.challenge.model.ResultOrder

class DefaultPriceDiscountLoader : PriceDiscountLoader {

    // let's just pretend this took a long time to process
    override fun getPriceDiscount(order: ResultOrder, listener: OnPriceDiscountResultListener) {
        val price = order.price.price
        val discount = when {
            price >= 150_000.0 -> getDiscount(0.5)
            price >= 100_000.0 -> getDiscount(0.3)
            else -> getDiscount(0.0)
        }
        listener(discount)
    }

    private fun getDiscount(discount: Double) =
        PriceDiscount(1.0 - discount)

}
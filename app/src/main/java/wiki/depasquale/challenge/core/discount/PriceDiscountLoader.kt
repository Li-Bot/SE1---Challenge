package wiki.depasquale.challenge.core.discount

import wiki.depasquale.challenge.model.PriceDiscount
import wiki.depasquale.challenge.model.ResultOrder

typealias OnPriceDiscountResultListener = (PriceDiscount) -> Unit

interface PriceDiscountLoader {

    fun getPriceDiscount(order: ResultOrder, listener: OnPriceDiscountResultListener)

}
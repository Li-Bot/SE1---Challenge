package wiki.depasquale.challenge.core.multiplier

import wiki.depasquale.challenge.model.PriceMultiplier
import wiki.depasquale.challenge.model.ResultOrder

typealias OnPriceMultiplierResultListener = (PriceMultiplier) -> Unit

interface PriceMultiplierLoader {

    fun getPriceMultiplier(order: ResultOrder, listener: OnPriceMultiplierResultListener)

}
package wiki.depasquale.challenge.core.pricing

import wiki.depasquale.challenge.model.Order
import wiki.depasquale.challenge.model.OrderPrice

typealias OnOrderCompleteListener = (result: Result<OrderPrice>) -> Unit

interface OrderPricingLoader {
    fun load(order: Order, completion: OnOrderCompleteListener)
}
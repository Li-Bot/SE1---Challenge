package wiki.depasquale.challenge.persistence

import wiki.depasquale.challenge.model.Order
import wiki.depasquale.challenge.model.OrderPrice

interface PersistableOrderPrice {

    fun getOrNull(order: Order): OrderPrice?

    fun put(order: Order, price: OrderPrice): Boolean

}

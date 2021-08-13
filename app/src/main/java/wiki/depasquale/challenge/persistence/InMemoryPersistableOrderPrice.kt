package wiki.depasquale.challenge.persistence

import wiki.depasquale.challenge.model.Order
import wiki.depasquale.challenge.model.OrderPrice

class InMemoryPersistableOrderPrice : PersistableOrderPrice {

    private val registry = hashMapOf<String, OrderPrice>()

    override fun getOrNull(order: Order): OrderPrice? {
        return synchronized(registry) {
            registry[order.id]
        }
    }

    override fun put(order: Order, price: OrderPrice): Boolean {
        return registry.put(order.id, price) == null
    }

}
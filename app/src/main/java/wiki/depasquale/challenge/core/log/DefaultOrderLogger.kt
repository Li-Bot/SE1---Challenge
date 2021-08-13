package wiki.depasquale.challenge.core.log

import wiki.depasquale.challenge.model.ResultOrder

class DefaultOrderLogger : OrderLogger {

    override fun log(order: ResultOrder) {
        println("Order: %s. Price: %.2f".format(order.order.id, order.price.price))
    }

}
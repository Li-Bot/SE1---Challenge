package wiki.depasquale.challenge.core.order

import wiki.depasquale.challenge.model.Order

interface OrderGenerator {

    fun next(): Order

}

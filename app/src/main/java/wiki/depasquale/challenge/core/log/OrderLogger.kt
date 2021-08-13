package wiki.depasquale.challenge.core.log

import wiki.depasquale.challenge.model.ResultOrder

interface OrderLogger {

    fun log(order: ResultOrder)

}
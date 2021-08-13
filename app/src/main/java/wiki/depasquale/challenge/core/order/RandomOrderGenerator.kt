package wiki.depasquale.challenge.core.order

import wiki.depasquale.challenge.model.Order
import java.util.*
import kotlin.random.Random

class RandomOrderGenerator(
    private val probability: Double
) : OrderGenerator {

    private var last: Order = createNewOrder()

    override fun next(): Order {
        if (shouldCreateNewOrder()) {
            last = createNewOrder()
        }
        return last
    }

    private fun createNewOrder() =
        Order(UUID.randomUUID().toString())

    private fun shouldCreateNewOrder() =
        getRandomProbability() < probability

    private fun getRandomProbability() =
        Random.nextDouble(0.0, 1.0)

}
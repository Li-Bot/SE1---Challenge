package wiki.depasquale.challenge.core.pricing

import wiki.depasquale.challenge.concurrent.DispatchQueue
import wiki.depasquale.challenge.model.Order
import wiki.depasquale.challenge.model.OrderPrice
import kotlin.random.Random

class OrderPricingLoaderException(message: String) : Exception(message)

class RandomOrderPricingLoader : OrderPricingLoader {
    override fun load(order: Order, completion: OnOrderCompleteListener) {
        performOnBackground {
            if (isFailure()) {
                completion(Result.failure(getPricingException()))
            } else {
                completion(Result.success(getRandomOrderPrice()))
            }
        }
    }

    private fun performOnBackground(action: Runnable) = DispatchQueue.execute(action)
    private fun isFailure() = Random.nextBoolean()
    private fun getPricingException() = OrderPricingLoaderException(":(")
    private fun getRandomOrderPrice() = OrderPrice(getRandomPrice())
    private fun getRandomPrice() = Random.nextDouble(1000.0, 200000.0)
}
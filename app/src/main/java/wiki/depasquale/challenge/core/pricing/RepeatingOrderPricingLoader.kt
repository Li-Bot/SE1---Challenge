package wiki.depasquale.challenge.core.pricing

import wiki.depasquale.challenge.model.Order

class RepeatingOrderPricingLoader private constructor(
    private val wrapped: OrderPricingLoader,
    private val repeatAtMost: Int
) : OrderPricingLoader {

    override fun load(order: Order, completion: OnOrderCompleteListener) {

        fun repeating(repeated: Int = 0): Unit = wrapped.load(order) { result ->
            result.onSuccess { completion(result) }
            result.onFailure {
                when (repeated) {
                    repeatAtMost -> completion(result)
                    else -> repeating(repeated + 1)
                }
            }
        }

        return repeating()
    }

    companion object {

        fun from(
            loader: OrderPricingLoader,
            repeatAtMost: Int = 1
        ) = RepeatingOrderPricingLoader(
            wrapped = loader,
            repeatAtMost = repeatAtMost
        )

    }

}
package wiki.depasquale.challenge.model

data class ResultOrder(
    val order: Order,
    val price: OrderPrice
) {

    inline fun updatePrice(action: (Double) -> Double): ResultOrder {
        val price = price
        val newPrice = price.copy(
            price = action(price.price)
        )
        return copy(price = newPrice)
    }

}
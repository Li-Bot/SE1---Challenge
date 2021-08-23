package wiki.depasquale.challenge.core.multiplier

import wiki.depasquale.challenge.model.PriceMultiplier
import wiki.depasquale.challenge.model.ResultOrder

class DefaultPriceMultiplierLoader : PriceMultiplierLoader {

    override fun getPriceMultiplier(order: ResultOrder, listener: OnPriceMultiplierResultListener) {
        listener(getGivenMultiplier())
    }

    private fun getGivenMultiplier(): PriceMultiplier {
        return PriceMultiplier(1.2) // idk, task says given, therefore I hereby give you 1.2
    }

}
package wiki.depasquale.challenge.core

class PriceCalculatorOptions private constructor(
    val repeatAtMost: Int
) {

    class Builder {

        private var repeatAtMost = 0

        fun setRepeatAtMost(repeatAtMost: Int) = apply {
            this.repeatAtMost = repeatAtMost
        }

        fun build(): PriceCalculatorOptions {
            return PriceCalculatorOptions(
                repeatAtMost = repeatAtMost
            )
        }

    }

    companion object {

        val default = Builder()
            .setRepeatAtMost(1)
            .build()

    }

}
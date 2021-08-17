package com.patlejch.se1challenge

interface MultiplierLoader {
    fun loadMultiplier(completion: (Result<PriceMultiplier>) -> Unit)
}

class ConstantMultiplierLoader(private val multiplier: Double) : MultiplierLoader {

    override fun loadMultiplier(completion: (Result<PriceMultiplier>) -> Unit) {
        completion(Result.success(PriceMultiplier(multiplier)))
    }
}

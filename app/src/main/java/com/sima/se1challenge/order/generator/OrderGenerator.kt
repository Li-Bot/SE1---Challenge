package com.sima.se1challenge.order.generator

import com.sima.se1challenge.model.Order

interface OrderGenerator {
    fun next(): Order

    companion object {
        fun getDefault(): OrderGenerator = RandomOrderGenerator(0.5)
    }
}

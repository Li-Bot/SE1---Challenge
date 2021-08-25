package com.sima.se1challenge

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.sima.se1challenge.order.OrderCalculator

class SEChallengeViewModel(
    private val orderCalculator: OrderCalculator
) : ViewModel() {


    var resultText = ObservableField("")

    fun calculate() {
        orderCalculator.calculate {
            it.fold({
                resultText.set("%.2f".format(it.price))
            }, {
                resultText.set("Error ${it.message}")
            })
        }
    }
}

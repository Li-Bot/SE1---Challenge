package com.patlejch.se1challenge

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.patlejch.se1challenge.uc.GetOrderWithPriceUC
import com.patlejch.se1challenge.uc.UseCase
import com.patlejch.se1challenge.uc.invoke

class MainViewModel(
    private val getOrderWithPriceUC: UseCase<Unit, GetOrderWithPriceUC.Out>
) : ViewModel() {

    val resultText = MutableLiveData("")

    fun compute() {
        getOrderWithPriceUC {
            it.onSuccess {
                resultText.value = it.orderPrice.toString()
            }.onFailure {
                resultText.value = "Failure"
            }
        }
    }
}

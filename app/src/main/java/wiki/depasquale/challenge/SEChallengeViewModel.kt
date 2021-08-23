package wiki.depasquale.challenge

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import wiki.depasquale.challenge.di.ServiceLocator

class SEChallengeViewModel : ViewModel() {

    var resultText = ObservableField("Press Run")
    var isRunning = ObservableBoolean(false)

    private val generator = ServiceLocator.getOrderGenerator()
    private val calculator = ServiceLocator.getPriceCalculator()

    fun onCalculatePrice() {
        isRunning.set(true)
        calculator.getPrice(generator.next()) { result ->
            val text = result.fold(
                onSuccess = { "%.2f CZK".format(it.price.price) },
                onFailure = { it.message ?: "Error" }
            )
            resultText.set(text)
            isRunning.set(false)
        }
    }

}
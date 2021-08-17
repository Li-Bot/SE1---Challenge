package com.patlejch.se1challenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.patlejch.se1challenge.di.seChallengeViewModel

class MainActivity : AppCompatActivity() {

    private val viewModelFactory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SEChallengeViewModel::class.java)) {
                return seChallengeViewModel() as T
            } else {
                throw IllegalStateException("Cannot create the required view model.")
            }
        }
    }

    private val viewModel: SEChallengeViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(SEChallengeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.main_button_compute).setOnClickListener {
            viewModel.compute()
        }

        viewModel.resultText.observe(this) {
            findViewById<TextView>(R.id.main_result).text = it
        }
    }
}

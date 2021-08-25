package com.sima.se1challenge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sima.se1challenge.databinding.ActivityMainBinding
import com.sima.se1challenge.di.DI
import com.sima.se1challenge.order.loader.DispatchQueue

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: SEChallengeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        viewModel =
            ViewModelProvider(this, ViewModelFactory()).get(SEChallengeViewModel::class.java)
        binding.viewModel = viewModel
    }

    override fun onDestroy() {
        super.onDestroy()
        DispatchQueue.shutdown()
    }

    class ViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return when {
                modelClass.isAssignableFrom(SEChallengeViewModel::class.java) -> DI.seChallengeViewModel() as T
                else -> throw IllegalStateException("Unknown ViewModel")
            }
        }
    }
}

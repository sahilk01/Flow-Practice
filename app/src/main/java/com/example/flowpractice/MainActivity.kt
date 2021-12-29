package com.example.flowpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var tv: TextView

    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv = findViewById(R.id.tv)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.actionMoviesFlow.filter {
                    when (it) {
                        is Outcome.Failure -> TODO()
                        is Outcome.Loading -> TODO()
                        is Outcome.Success -> it.data.id < 4
                    }
                }.collect {
                    when (it) {
                        is Outcome.Failure -> showError(it.throwable)
                        is Outcome.Loading -> showLoading()
                        is Outcome.Success -> tv.text = it.data.title
                    }
                }
            }
        }
    }

    private fun showLoading() {
        tv.text = getString(R.string.loading)
    }


    private fun showError(throwable: Throwable) {
        Toast.makeText(this, throwable.localizedMessage, Toast.LENGTH_LONG).show()
    }
}
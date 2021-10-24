package com.example.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coroutines.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!

    private val RESULT_1 = "Result #1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(binding) {
            button.setOnClickListener {

                CoroutineScope(IO).launch {
                    fakeApiRequest()
                }
            }
        }
    }

    private fun setNewText(input: String){
        val newText = binding.text.text.toString() + "\n$input"
        binding.text.text = newText
    }

    private suspend fun setTextOnMainThread(input: String){
        withContext(Main){
            setNewText(input)
        }
    }

    private suspend fun fakeApiRequest() {
        val result1 = getResultFromApi()
        println("debug: $result1")
        setTextOnMainThread(result1)
    }

    private suspend fun getResultFromApi(): String {
        logThread("getResultFromApi")
        delay(1000)
        return RESULT_1
    }

    private fun logThread(methodName: String){
        println("debug: ${methodName}: ${Thread.currentThread().name}")
    }
}
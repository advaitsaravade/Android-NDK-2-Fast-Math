package com.example.ndkhelloworld

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log.i
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    companion object {
        // Load native library
        init {
            System.loadLibrary("native-lib")
        }
    }

    external fun fibonacciNative(n: Int): Int
    external fun factorialNative(n: Int): Int

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Fibonacci

        val n = 10000  // Change this value to test different scenarios
        val iterations = 1000  // Running multiple times for better benchmarking
        val resultText = findViewById<TextView>(R.id.textView1)

        // Measure Kotlin Iterative Fibonacci
        val kotlinIterTime = benchmark(iterations) { fibonacciKotlin(n) }

        // Measure Native Iterative Fibonacci
        val nativeIterTime = benchmark(iterations) { fibonacciNative(n) }

        resultText.text = """
        Fibonacci Kotlin Iterative: Processed in $kotlinIterTime ns
        
        Fibonacci Native Iterative: Processed in $nativeIterTime ns
    """.trimIndent()

        // Factorial
        val f = 20  // Change this value to test different scenarios
        val resultText2 = findViewById<TextView>(R.id.textView2)

        // Measure Kotlin Iterative Fibonacci
        val kotlinIterTimeFact = benchmark(iterations) { factorialKotlin(f) }

        // Measure Native Iterative Fibonacci
        val nativeIterTimeFact = benchmark(iterations) { factorialNative(f) }

        resultText2.text = """
        Factorial Kotlin Iterative: Processed in $kotlinIterTimeFact ns
        
        Factorial Native Iterative: Processed in $nativeIterTimeFact ns
    """.trimIndent()
    }

    private fun fibonacciKotlin(n: Int): Int {
        if (n <= 1) return n
        var a = 0
        var b = 1
        for (i in 2..n) {
            val temp = a + b
            a = b
            b = temp
        }
        return b
    }

    private fun factorialKotlin(n: Int): Int {
        if (n < 0) return -1 // Return -1 for invalid input (negative numbers)
        var result = 1
        for (i in 2..n) {
            result *= i
        }
        return result
    }

    // Benchmarking utility to measure average execution time
    private fun benchmark(iterations: Int, function: () -> Int): Long {
        var totalTime = 0L
        repeat(iterations) {
            val start = System.nanoTime()
            function()
            totalTime += (System.nanoTime() - start)
        }
        return totalTime / iterations  // Return the average time
    }
}
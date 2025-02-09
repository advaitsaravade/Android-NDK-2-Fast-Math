package com.example.ndkhelloworld

import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val n = 10000  // Change this value to test different scenarios
        val iterations = 1000  // Running multiple times for better benchmarking
        val resultText = findViewById<TextView>(R.id.textView1)

        // Measure Kotlin Iterative Fibonacci
        val kotlinIterTime = benchmark(iterations) { fibonacciKotlin(n) }

        // Measure Native Iterative Fibonacci
        val nativeIterTime = benchmark(iterations) { fibonacciNative(n) }

        resultText.text = """
        Kotlin Iterative: Processed in ${kotlinIterTime} ns
        
        Native Iterative: Processed in ${nativeIterTime} ns
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
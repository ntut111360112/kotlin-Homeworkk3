package com.example.kotlinlab9_2

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    // Declare UI components
    private lateinit var btnCalculate: Button
    private lateinit var edHeight: EditText
    private lateinit var edWeight: EditText
    private lateinit var edAge: EditText
    private lateinit var tvWeightResult: TextView
    private lateinit var tvFatResult: TextView
    private lateinit var tvBmiResult: TextView
    private lateinit var tvProgress: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var llProgress: LinearLayout
    private lateinit var btnBoy: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enableEdgeToEdge()

        // Setup window insets to ensure correct layout with system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Bind UI components
        btnCalculate = findViewById(R.id.btnCalculate)
        edHeight = findViewById(R.id.edHeight)
        edWeight = findViewById(R.id.edWeight)
        edAge = findViewById(R.id.edAge)
        tvWeightResult = findViewById(R.id.tvWeightResult)
        tvFatResult = findViewById(R.id.tvFatResult)
        tvBmiResult = findViewById(R.id.tvBmiResult)
        tvProgress = findViewById(R.id.tvProgress)
        progressBar = findViewById(R.id.progressBar)
        llProgress = findViewById(R.id.llProgress)
        btnBoy = findViewById(R.id.btnBoy)

        // Set button click listener for calculation
        btnCalculate.setOnClickListener {
            if (isInputValid()) {
                calculateBodyMetrics()
            }
        }
    }

    // Check if the user input is valid
    private fun isInputValid(): Boolean {
        return when {
            edHeight.text.isEmpty() -> {
                showToast("請輸入身高")
                false
            }
            edWeight.text.isEmpty() -> {
                showToast("請輸入體重")
                false
            }
            edAge.text.isEmpty() -> {
                showToast("請輸入年齡")
                false
            }
            else -> true
        }
    }

    // Show a toast message
    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    // Simulate the calculation process and update the results
    private fun calculateBodyMetrics() {
        // Clear previous results and reset the progress bar
        resetUI()

        Thread {
            var progress = 0
            while (progress < 100) {
                try {
                    Thread.sleep(50)
                } catch (ignored: InterruptedException) {
                }
                progress++
                runOnUiThread {
                    progressBar.progress = progress
                    tvProgress.text = "$progress%"
                }
            }

            // Get user input and handle potential number format exceptions
            val height = try {
                edHeight.text.toString().toDouble()
            } catch (e: NumberFormatException) {
                showToast("身高輸入錯誤")
                return@Thread
            }

            val weight = try {
                edWeight.text.toString().toDouble()
            } catch (e: NumberFormatException) {
                showToast("體重輸入錯誤")
                return@Thread
            }

            val age = try {
                edAge.text.toString().toDouble()
            } catch (e: NumberFormatException) {
                showToast("年齡輸入錯誤")
                return@Thread
            }

            // Calculate BMI
            val bmi = weight / (height / 100).pow(2)

            // Calculate standard weight and body fat based on gender
            val (standardWeight, bodyFat) = if (btnBoy.isChecked) {
                Pair((height - 80) * 0.7, 1.39 * bmi + 0.16 * age - 19.34)
            } else {
                Pair((height - 70) * 0.6, 1.39 * bmi + 0.16 * age - 9)
            }

            // Update UI on the main thread
            runOnUiThread {
                llProgress.visibility = View.GONE
                tvWeightResult.text = "標準體重 \n${String.format("%.2f", standardWeight)}"
                tvFatResult.text = "體脂肪 \n${String.format("%.2f", bodyFat)}"
                tvBmiResult.text = "BMI \n${String.format("%.2f", bmi)}"
            }
        }.start()
    }

    // Reset the UI elements before new calculations
    private fun resetUI() {
        tvWeightResult.text = "標準體重\n無"
        tvFatResult.text = "體脂肪\n無"
        tvBmiResult.text = "BMI\n無"
        progressBar.progress = 0
        tvProgress.text = "0%"
        llProgress.visibility = View.VISIBLE
    }
}

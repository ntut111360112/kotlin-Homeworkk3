package com.example.kotlinlab9_1

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private var progressRabbit = 0
    private var progressTurtle = 0
    private lateinit var btnStart: Button
    private lateinit var sbRabbit: SeekBar
    private lateinit var sbTurtle: SeekBar

    // Handler to update progress bars
    private val handler = Handler(Looper.getMainLooper()) { msg ->
        when (msg.what) {
            1 -> updateProgress(sbRabbit, progressRabbit, "兔子勝利")
            2 -> updateProgress(sbTurtle, progressTurtle, "烏龜勝利")
        }
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Bind UI components
        btnStart = findViewById(R.id.btnStart)
        sbRabbit = findViewById(R.id.sbRabbit)
        sbTurtle = findViewById(R.id.sbTurtle)

        // Set start button listener
        btnStart.setOnClickListener {
            startRace()
        }

        // Handle window insets for edge-to-edge UI
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun startRace() {
        // Disable start button during race
        btnStart.isEnabled = false

        // Reset progress values
        progressRabbit = 0
        progressTurtle = 0
        sbRabbit.progress = 0
        sbTurtle.progress = 0

        // Start rabbit and turtle threads
        Thread { runRabbit() }.start()
        Thread { runTurtle() }.start()
    }

    private fun runRabbit() {
        val sleepProbability = arrayOf(true, true, false)
        while (progressRabbit < 100 && progressTurtle < 100) {
            try {
                Thread.sleep(100) // Delay for 0.1 second
                if (sleepProbability.random()) Thread.sleep(300) // Rabbit rests 0.3 second
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            progressRabbit += 3
            sendMessage(1)
        }
    }

    private fun runTurtle() {
        while (progressTurtle < 100 && progressRabbit < 100) {
            try {
                Thread.sleep(100) // Delay for 0.1 second
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            progressTurtle += 1
            sendMessage(2)
        }
    }

    private fun sendMessage(winner: Int) {
        val msg = handler.obtainMessage(winner)
        handler.sendMessage(msg)
    }

    private fun updateProgress(seekBar: SeekBar, progress: Int, winnerMessage: String) {
        seekBar.progress = progress
        if (progress >= 100) {
            showToast(winnerMessage)
            btnStart.isEnabled = true
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}

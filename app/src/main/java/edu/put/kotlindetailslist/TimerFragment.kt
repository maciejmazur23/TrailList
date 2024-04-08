package edu.put.kotlindetailslist

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class TimerFragment : Fragment() {

    private lateinit var timerTextView: TextView
    private lateinit var startButton: Button
    private lateinit var stopButton: Button
    private lateinit var resumeButton: Button
    private lateinit var resetButton: Button
    private lateinit var pauseButton: Button

    private var running: Boolean = false
    private var pausedTime: Long = 0
    private var startTime: Long = 0

    private val handler = Handler()
    private val timerRunnable = object : Runnable {
        override fun run() {
            val currentTime = SystemClock.elapsedRealtime()
            val elapsedTime = currentTime - startTime + pausedTime

            val hours = (elapsedTime / 3600000).toInt()
            val minutes = ((elapsedTime - hours * 3600000) / 60000).toInt()
            val seconds = ((elapsedTime - hours * 3600000 - minutes * 60000) / 1000).toInt()

            timerTextView.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)

            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dynamic_stoper, container, false)

        timerTextView = view.findViewById(R.id.timerTextView)
        startButton = view.findViewById(R.id.startButton)
        stopButton = view.findViewById(R.id.stopButton)
        resumeButton = view.findViewById(R.id.resumeButton)
        resetButton = view.findViewById(R.id.resetButton)
        pauseButton = view.findViewById(R.id.pauseButton)

        startButton.setOnClickListener {
            startTimer()
        }

        stopButton.setOnClickListener {
            stopTimer()
        }

        pauseButton.setOnClickListener {
            pauseTimer()
        }

        resumeButton.setOnClickListener {
            resumeTimer()
        }

        resetButton.setOnClickListener {
            resetTimer()
        }

        return view
    }

    private fun startTimer() {
        if (!running) {
            startTime = SystemClock.elapsedRealtime()
            handler.postDelayed(timerRunnable, 0)
            running = true
        }
    }

    private fun stopTimer() {
        if (running) {
            handler.removeCallbacks(timerRunnable)
            pausedTime += SystemClock.elapsedRealtime() - startTime
            running = false
        }
    }

    private fun pauseTimer() {
        if (running) {
            handler.removeCallbacks(timerRunnable)
            pausedTime += SystemClock.elapsedRealtime() - startTime
            running = false
        }
    }

    private fun resumeTimer() {
        if (!running && startTime>0) {
            startTime = SystemClock.elapsedRealtime()
            handler.postDelayed(timerRunnable, 0)
            running = true
        }
    }

    @SuppressLint("SetTextI18n")
    private fun resetTimer() {
        if (!running) {
            pausedTime = 0
            timerTextView.setText("00:00:00")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(timerRunnable)
    }
}
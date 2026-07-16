package com.example.testlayout

import android.os.SystemClock
import android.util.Log
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Date

class StopwatchRunnable(
    @Suppress("UNUSED_PARAMETER") fragment: RunFragment,
    private val stopwatchTextView: TextView
) : Runnable {
    private val monitor = Object()
    private val timeFormat = SimpleDateFormat("mm:ss:SS")

    @Volatile
    private var paused = true

    @Volatile
    var totalTime: String = ""
        private set

    fun pause() {
        synchronized(monitor) {
            paused = true
        }
    }

    fun resume() {
        synchronized(monitor) {
            paused = false
            monitor.notifyAll()
        }
    }

    override fun run() {
        while (!Thread.currentThread().isInterrupted) {
            try {
                synchronized(monitor) {
                    while (paused) {
                        monitor.wait()
                    }
                }
            } catch (exception: InterruptedException) {
                pause()
                totalTime = stopwatchTextView.text.toString()
                synchronized(StopwatchRunnable::class.java) {
                    totalRuns += 1
                }
                return
            }

            stopwatchTextView.text = timeFormat.format(Date(SystemClock.currentThreadTimeMillis()))
            totalTime = stopwatchTextView.text.toString()
        }

        Log.w("RunFragment", "exited")
    }

    companion object {
        var totalRuns: Int = 0
    }
}

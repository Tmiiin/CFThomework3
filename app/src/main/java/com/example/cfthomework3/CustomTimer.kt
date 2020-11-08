package com.example.cfthomework3

import android.os.Handler
import android.os.Looper
import android.widget.TextView
import java.util.*

class CustomTimer(
    private val timerText: TextView,
    name: String,
    var seconds: Int
) : Thread(name) {

    companion object {
        lateinit var timeLeftFormatted: String
        var isRunning = false
    }

    var secondsCounter = seconds
    var handler = Handler(Looper.getMainLooper())

    override fun run() {
        isRunning = true
        while (secondsCounter > 0) {
            if (isRunning) {
                timeLeftFormatted = setText(secondsCounter)

                handler.post {
                    timerText.text = timeLeftFormatted
                }
                sleep(1000)
                secondsCounter--
            }
        }
    }

    private fun setText(counter: Int): String {
        val hours: Int = counter / 3600
        val minutes: Int = counter / 60
        val seconds = counter % 60
        if (hours > 0) {
            timeLeftFormatted = String.format(
                Locale.getDefault(),
                "%d:%02d:%02d", hours, minutes, seconds
            );
        } else {
            timeLeftFormatted = String.format(
                Locale.getDefault(),
                "%02d:%02d", minutes, seconds
            )
        }
        return timeLeftFormatted
    }

    fun stopTimer() {
        isRunning = false
    }

    fun startTimer() {
        isRunning = true
    }

}
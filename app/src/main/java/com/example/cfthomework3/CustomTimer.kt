package com.example.cfthomework3

import android.os.Handler
import android.os.Looper
import java.util.*

class CustomTimer(
    name: String,
    seconds: Int,
    private val callback: (String) -> Unit
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
                    callback(timeLeftFormatted)
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
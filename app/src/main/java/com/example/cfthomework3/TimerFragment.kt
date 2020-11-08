package com.example.cfthomework3

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.timer_fragment.*


class TimerFragment(private val mContext: Context) : Fragment() {

    private var isRunning = false
    private var name = "Таймер"
    private val startWork = "Возобновить работу"
    private val stopWork = "Поставить на паузу"
    private var customTimer: CustomTimer? = null

    companion object {
        fun newInstance(context: Context) = TimerFragment(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.timer_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        hideProgressBar()
        timer_button.setOnClickListener {
            onTimerButtonClick()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun onTimerButtonClick() {
        if (!isRunning && customTimer != null) {
            isRunning = true
            showProgressBar()
            timer_button.text = stopWork
            customTimer!!.startTimer()
        } else if (!isRunning) {
            isRunning = true
            showProgressBar()
            timer_button.text = stopWork
            startTimer()
        } else {
            isRunning = false
            hideProgressBar()
            timer_button.text = startWork
            customTimer?.stopTimer()
        }
    }

    private fun startTimer() {
        showProgressBar()
        customTimer = if (timer_edit_text.text.isNotBlank())
            CustomTimer(
                timer_text, name,
                timer_edit_text.text.toString().toInt() * 60
            )
        else
            CustomTimer(
                timer_text, name,
                60
            )
        timer_edit_text.visibility = View.INVISIBLE
        customTimer?.start()
    }

    private fun hideProgressBar() {
        timer_progress_bar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        timer_progress_bar.visibility = View.VISIBLE
    }

    private fun saveInfoOfTime() {
        val prefs: SharedPreferences = mContext.getSharedPreferences("prefs", MODE_PRIVATE)
        val editor = prefs.edit()
        customTimer?.secondsCounter?.let { editor.putInt("leftTimeInSeconds", it) }
        editor.apply()
    }

    private fun getInfoOfTime(): Int {
        val prefs: SharedPreferences = mContext.getSharedPreferences("prefs", MODE_PRIVATE)
        return if(prefs.contains("leftTimeInSeconds"))
            prefs.getInt("leftTimeInSeconds", 0)
        else 0
    }

}
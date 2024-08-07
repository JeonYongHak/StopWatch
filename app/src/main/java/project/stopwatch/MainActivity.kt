package project.stopwatch

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import java.util.Timer
import kotlin.concurrent.timer

class MainActivity : ComponentActivity(), View.OnClickListener {

    var isRunning = false
    var timer: Timer? = null
    var time = 0

    private lateinit var start: Button
    private lateinit var refresh: Button
    private lateinit var millisecond: TextView
    private lateinit var second: TextView
    private lateinit var minute: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start = findViewById(R.id.start)
        refresh = findViewById(R.id.refresh)
        millisecond = findViewById(R.id.millisecond)
        second = findViewById(R.id.second)
        minute = findViewById(R.id.minute)

        start.setOnClickListener(this)
        refresh.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.start -> {
                if (isRunning) {
                    pause()
                } else {
                    start()
                }
            }
            R.id.refresh -> {
                refresh()
            }
        }
    }

    private fun start() {
        start.text = "일시정지"
        start.setBackgroundColor(getColor(R.color.red))
        isRunning = true

        timer = timer(period = 10) {
            time++

            val milliSecond = time % 100
            val secondValue = (time % 6000) / 100
            val minuteValue = time / 6000

            runOnUiThread {
                if (isRunning) {
                    millisecond.text = if (milliSecond < 10) ".0${milliSecond}" else ".${milliSecond}"
                    second.text = if (secondValue < 10) ":0${secondValue}" else ":${secondValue}"
                    minute.text = "${minuteValue}"
                }
            }
        }
    }

    private fun pause() {
        start.text = "시작"
        start.setBackgroundColor(getColor(R.color.blue))

        isRunning = false
        timer?.cancel()


    }

    private fun refresh() {

        start.text = "시작"
        start.setBackgroundColor(getColor(R.color.blue))

        isRunning = false
        timer?.cancel()

        time = 0
        millisecond.text = ".00"
        second.text = ":00"
        minute.text = "00"

    }
}

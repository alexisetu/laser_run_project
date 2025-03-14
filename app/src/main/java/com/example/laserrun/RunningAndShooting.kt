package com.example.laserrun

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import android.widget.Chronometer
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.laserrun.databinding.RunningAndShootingBinding

abstract class RunningAndShooting : AppCompatActivity() {
    abstract val destination: Class<*>
    abstract val buttonText: String
    abstract val fondColor: Int
    abstract val isLastLap: Boolean

    protected lateinit var binding: RunningAndShootingBinding
    protected lateinit var chronometer1: Chronometer
    protected lateinit var chronometer2: Chronometer
    private lateinit var statusText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RunningAndShootingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        chronometer1 = binding.chronometer1
        chronometer2 = binding.chronometer2
        statusText = binding.statusText

        chronometer1.format = "Temps global : %s"
        chronometer2.format = "Temps tour : %s"

        binding.button.text = buttonText
        binding.runningpage.setBackgroundColor(fondColor)

        val chronometer1Base = intent.getLongExtra("CHRONOMETER1_BASE", SystemClock.elapsedRealtime())
        val chronometer2Base = intent.getLongExtra("CHRONOMETER2_BASE", SystemClock.elapsedRealtime())

        chronometer1.base = chronometer1Base
        chronometer1.start()

        chronometer2.base = chronometer2Base
        chronometer2.start()

        updateStatusText()

        binding.button.setOnClickListener {
            val currentLap = intent.getIntExtra("CURRENT_LAP", 0)
            val lapCount = intent.getIntExtra("LAP_COUNT", 0)
            val newLapTime = SystemClock.elapsedRealtime() - chronometer2.base

            val runTimes = intent.getLongArrayExtra("RUN_TIMES") ?: longArrayOf()
            val lapTimes = intent.getLongArrayExtra("LAP_TIMES") ?: longArrayOf()
            val targetsHitArray = intent.getIntArrayExtra("TARGETS_HIT_ARRAY") ?: IntArray(lapCount) { 5 }

            val updatedRunTimes = if (this is Running) runTimes + newLapTime else runTimes
            val updatedLapTimes = if (this is Shooting) lapTimes + newLapTime else lapTimes

            if (this is Running && isLastLap) {
                val totalTime = SystemClock.elapsedRealtime() - chronometer1.base
                val intent = Intent(this, FinishActivity::class.java).apply {
                    putExtra("TOTAL_TIME", totalTime)
                    putExtra("RUN_TIMES", updatedRunTimes)
                    putExtra("LAP_TIMES", updatedLapTimes)
                    putExtra("TARGETS_HIT_ARRAY", targetsHitArray)
                }
                startActivity(intent)
                finish()
            } else {
                chronometer2.base = SystemClock.elapsedRealtime()
                chronometer2.start()

                val nextLap = if (this@RunningAndShooting is Running) currentLap + 1 else currentLap

                val intent = Intent(this, destination).apply {
                    putExtra("CHRONOMETER1_BASE", chronometer1.base)
                    putExtra("CHRONOMETER2_BASE", chronometer2.base)
                    putExtra("RUN_TIMES", updatedRunTimes)
                    putExtra("LAP_TIMES", updatedLapTimes)
                    putExtra("CURRENT_LAP", nextLap)
                    putExtra("LAP_COUNT", lapCount)
                    putExtra("TARGETS_HIT_ARRAY", targetsHitArray)
                }
                startActivity(intent)
                finish()
            }
        }
    }
    private fun updateStatusText() {
        val currentLap = intent.getIntExtra("CURRENT_LAP", 0)
        val lapCount = intent.getIntExtra("LAP_COUNT", 0)

        val status = when {
            currentLap == 0 -> "Tour initial"
            this is Shooting -> "Stand $currentLap/$lapCount"
            this is Running -> "Course $currentLap/$lapCount"
            else -> ""
        }

        statusText.text = status
    }
}
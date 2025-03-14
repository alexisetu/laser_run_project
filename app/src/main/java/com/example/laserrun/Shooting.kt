package com.example.laserrun

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.content.ContextCompat

class Shooting : RunningAndShooting() {
    override val destination: Class<*> = Running::class.java
    override val buttonText: String by lazy { getString(R.string.cinqcibles) }
    override val fondColor: Int by lazy { getColor(R.color.fondviolet) }
    override val isLastLap: Boolean by lazy { false }

    private var targetsHit: Int = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val targetButtonsLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(16, 16, 16, 16)
            }
            gravity = android.view.Gravity.CENTER
        }

        val targets = listOf("4", "3", "2", "1", "0")
        for (target in targets) {
            val button = Button(this).apply {
                text = target
                layoutParams = LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    weight = 1f
                    setMargins(16, 16, 16, 16)
                }
                setBackgroundColor(ContextCompat.getColor(context, R.color.boutonviolet))
                setTextColor(Color.WHITE)

                setOnClickListener {
                    targetsHit = target.toInt()

                    binding.button.performClick()

                }
            }
            targetButtonsLayout.addView(button)
        }

        val mainLayout = findViewById<LinearLayout>(R.id.runningpage)
        mainLayout.addView(targetButtonsLayout, mainLayout.childCount - 1) // Ajouter avant le bottom_space

        binding.button.setOnClickListener(null)
        binding.button.setOnClickListener {
            val currentLap = intent.getIntExtra("CURRENT_LAP", 0)
            val lapCount = intent.getIntExtra("LAP_COUNT", 0)
            val newLapTime = SystemClock.elapsedRealtime() - chronometer2.base

            val runTimes = intent.getLongArrayExtra("RUN_TIMES") ?: longArrayOf()
            val lapTimes = intent.getLongArrayExtra("LAP_TIMES") ?: longArrayOf()

            val targetsHitArray = intent.getIntArrayExtra("TARGETS_HIT_ARRAY") ?: IntArray(lapCount) { 5 }

            if (currentLap - 1 >= 0 && currentLap - 1 < targetsHitArray.size) {
                targetsHitArray[currentLap - 1] = targetsHit
            }

            val updatedRunTimes = runTimes
            val updatedLapTimes = lapTimes + newLapTime

            chronometer2.base = SystemClock.elapsedRealtime()
            chronometer2.start()

            val nextLap = currentLap

            val intent = Intent(this, destination).apply {
                putExtra("CHRONOMETER1_BASE", chronometer1.base)
                putExtra("CHRONOMETER2_BASE", chronometer2.base)
                putExtra("RUN_TIMES", updatedRunTimes)
                putExtra("LAP_TIMES", updatedLapTimes)
                putExtra("CURRENT_LAP", nextLap)
                putExtra("LAP_COUNT", lapCount)
                putExtra("TARGETS_HIT", targetsHit)
                putExtra("TARGETS_HIT_ARRAY", targetsHitArray)
            }
            startActivity(intent)
            finish()
        }
    }
}
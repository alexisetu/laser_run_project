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
    private var isAlertShown = false

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

        // Ajouter un écouteur pour vérifier le temps écoulé
        if (this is Shooting) {
            chronometer2.setOnChronometerTickListener { chronometer ->
                val elapsedMillis = SystemClock.elapsedRealtime() - chronometer.base
                val elapsedSeconds = elapsedMillis / 1000
                
                // Si le temps écoulé est supérieur ou égal à 50 secondes et que l'alerte n'a pas encore été affichée
                if (elapsedSeconds >= 50 && !isAlertShown) {
                    // Changer la couleur du texte du chronomètre en rouge
                    chronometer.setTextColor(Color.RED)
                    
                    // Faire clignoter le fond de l'écran
                    binding.runningpage.setBackgroundColor(Color.RED)
                    binding.runningpage.postDelayed({
                        binding.runningpage.setBackgroundColor(fondColor)
                    }, 500)
                    
                    // Faire vibrer le téléphone (nécessite la permission VIBRATE)
                    val vibrator = getSystemService(VIBRATOR_SERVICE) as android.os.Vibrator
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        vibrator.vibrate(android.os.VibrationEffect.createOneShot(500, android.os.VibrationEffect.DEFAULT_AMPLITUDE))
                    } else {
                        @Suppress("DEPRECATION")
                        vibrator.vibrate(500)
                    }
                    
                    isAlertShown = true
                }
                
                // Si le temps écoulé est inférieur à 50 secondes et que l'alerte a été affichée (réinitialisation)
                if (elapsedSeconds < 50 && isAlertShown) {
                    chronometer.setTextColor(Color.WHITE)
                    isAlertShown = false
                }
            }
        }

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
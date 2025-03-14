package com.example.laserrun

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.laserrun.databinding.ActivityFinishBinding
import java.util.concurrent.TimeUnit

class FinishActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFinishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val totalTime = intent.getLongExtra("TOTAL_TIME", 0) // Récupérer le temps total
        val runTimes = intent.getLongArrayExtra("RUN_TIMES") ?: longArrayOf() // Récupérer les temps de course
        val lapTimes = intent.getLongArrayExtra("LAP_TIMES") ?: longArrayOf() // Récupérer les temps de stands

        binding.totalTime.text = getString(R.string.temps_total, formatTime(totalTime))

        val runTime = runTimes.sum() // Somme des temps de course
        val standTime = lapTimes.sum() // Somme des temps de stands

        binding.runTime.text = getString(R.string.temps_vitesse_run, formatTime(runTime))

        binding.standTime.text = getString(R.string.temps_stand, formatTime(standTime))

        val lapTimesText = StringBuilder()
        for (i in runTimes.indices) {
            lapTimesText.append("Tour $i : ${formatTime(runTimes[i])}\n") // Ajouter chaque temps de course
        }
        binding.lapTimes.text = lapTimesText.toString().trim() // Afficher dans le TextView

        val standTimesText = StringBuilder()
        for (i in lapTimes.indices) {
            standTimesText.append("Stand ${i + 1} : 5 tirs (${formatTime(lapTimes[i])})\n") // Ajouter chaque temps de stand
        }
        binding.standTimes.text = standTimesText.toString().trim()

        val missedTargets = 0
        val avgStandTime = if (lapTimes.isNotEmpty()) {
            lapTimes.map { formatTime(it).removeSuffix(" s").toDouble() }.average() // Convertir en secondes et calculer la moyenne
        } else {
            0.0
        }
        val shootingStatsText = "Cibles ratés : $missedTargets\n" +
                "Temps moyen sur le stand : ${String.format("%.1f", avgStandTime)} s"
        binding.shootingStats.text = shootingStatsText

        binding.btnClose.setOnClickListener {
            val intent = Intent(this, CategoryChoice::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }

    private fun formatTime(timeInMillis: Long): String {
        val seconds = timeInMillis / 1000 // Convertir les millisecondes en secondes
        return String.format("%d s", seconds) // Afficher en secondes
    }

    private fun calculateSpeed(timeInMillis: Long, distanceInMeters: Int): Double {
        val timeInHours = timeInMillis / (1000.0 * 60.0 * 60.0)
        val distanceInKm = distanceInMeters / 1000.0
        return if (timeInHours > 0) distanceInKm / timeInHours else 0.0
    }
}
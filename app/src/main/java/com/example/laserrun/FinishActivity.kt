package com.example.laserrun

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.laserrun.databinding.ActivityFinishBinding

class FinishActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFinishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val totalTime = intent.getLongExtra("TOTAL_TIME", 0)
        val runTimes = intent.getLongArrayExtra("RUN_TIMES") ?: longArrayOf()
        val lapTimes = intent.getLongArrayExtra("LAP_TIMES") ?: longArrayOf()
        val targetsHitArray = intent.getIntArrayExtra("TARGETS_HIT_ARRAY") ?: IntArray(lapTimes.size) { 5 }

        binding.totalTime.text = getString(R.string.temps_total, formatTime(totalTime))

        val runTime = runTimes.sum()
        val standTime = lapTimes.sum()

        binding.runTime.text = getString(R.string.temps_run, formatTime(runTime))
        binding.standTime.text = getString(R.string.temps_stand, formatTime(standTime))

        val lapTimesText = StringBuilder()
        for (i in runTimes.indices) {
            lapTimesText.append(getString(R.string.tour_format, i, formatTime(runTimes[i])))
            lapTimesText.append("\n")
        }
        binding.lapTimes.text = lapTimesText.toString().trim()

        val standTimesText = StringBuilder()
        var totalTargetsHit = 0
        for (i in lapTimes.indices) {
            val targetsHit = if (i < targetsHitArray.size) targetsHitArray[i] else 5
            totalTargetsHit += targetsHit
            standTimesText.append(getString(R.string.stand_format, i + 1, targetsHit, formatTime(lapTimes[i])))
            standTimesText.append("\n")
        }
        binding.standTimes.text = standTimesText.toString().trim()

        val totalPossibleTargets = lapTimes.size * 5
        val missedTargets = totalPossibleTargets - totalTargetsHit
        val avgStandTime = if (lapTimes.isNotEmpty()) {
            lapTimes.map { formatTime(it).removeSuffix(" s").toDouble() }.average()
        } else {
            0.0
        }
        
        val shootingStatsText = getString(R.string.cibles_ratees, missedTargets) + "\n" +
                getString(R.string.temps_moyen_stand, String.format("%.1f", avgStandTime))
        binding.shootingStats.text = shootingStatsText

        binding.btnClose.setOnClickListener {
            val intent = Intent(this, CategoryChoice::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }

    private fun formatTime(timeInMillis: Long): String {
        val seconds = timeInMillis / 1000
        return String.format("%d s", seconds)
    }
}
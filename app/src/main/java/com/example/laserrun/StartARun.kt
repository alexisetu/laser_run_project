package com.example.laserrun

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import com.example.laserrun.databinding.StartARunBinding

class StartARun : AppCompatActivity() {
    private lateinit var binding: StartARunBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = StartARunBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val categoryName = intent.getStringExtra("categoryName")
        val initialDistance = intent.getStringExtra("INITIAL_DISTANCE")
        val lapDistance = intent.getStringExtra("LAP_DISTANCE")
        val lapCount = intent.getIntExtra("LAP_COUNT", 0)
        val shootDistance = intent.getStringExtra("SHOOT_DISTANCE")

        binding.category.text = categoryName
        binding.initialDistance.text = "Distance initiale: $initialDistance m"
        binding.lap.text = "Tours: $lapCount x $lapDistance m"
        binding.shootDistance.text = "Distance de tir: $shootDistance m"

        binding.depart.setOnClickListener{
            val startTime = SystemClock.elapsedRealtime()

            val lapCount = intent.getIntExtra("LAP_COUNT", 0)
            val currentLap = intent.getIntExtra("CURRENT_LAP", 0)

            val intent = Intent(this, Running::class.java)
            intent.putExtra("CHRONOMETER1_BASE", startTime)
            intent.putExtra("CHRONOMETER2_BASE", startTime)
            intent.putExtra("LAP_COUNT", lapCount)
            intent.putExtra("CURRENT_LAP", currentLap)
            intent.putExtra("LAP_TIMES", longArrayOf())
            startActivity(intent)
            finish()
        }
    }


}
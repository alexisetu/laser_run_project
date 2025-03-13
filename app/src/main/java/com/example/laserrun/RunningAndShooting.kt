package com.example.laserrun

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import android.widget.Chronometer
import androidx.appcompat.app.AppCompatActivity
import com.example.laserrun.databinding.RunningAndShootingBinding

abstract class RunningAndShooting : AppCompatActivity() {
    abstract val destination: Class<*>
    abstract val buttonText: String
    abstract val fondColor: Int

    private lateinit var binding: RunningAndShootingBinding
    private lateinit var chronometer1: Chronometer
    private lateinit var chronometer2: Chronometer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RunningAndShootingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        chronometer1 = binding.chronometer1
        chronometer2 = binding.chronometer2

        binding.button.text = buttonText
        binding.runningpage.setBackgroundColor(fondColor)

        val chronometer1Base = intent.getLongExtra("CHRONOMETER1_BASE", SystemClock.elapsedRealtime())
        val chronometer2Base = intent.getLongExtra("CHRONOMETER2_BASE", SystemClock.elapsedRealtime())

        chronometer1.base = chronometer1Base
        chronometer1.start()

        chronometer2.base = chronometer2Base
        chronometer2.start()

        binding.button.setOnClickListener{
            chronometer2.base = SystemClock.elapsedRealtime()
            chronometer2.start()

            val intent = Intent(this, destination)
            intent.putExtra("CHRONOMETER1_BASE", chronometer1.base)
            intent.putExtra("CHRONOMETER2_BASE", chronometer2.base)
            startActivity(intent)
            finish()
        }
    }
}
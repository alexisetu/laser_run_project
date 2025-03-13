package com.example.laserrun

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.laserrun.databinding.RunningAndShootingBinding

abstract class RunningAndShooting : AppCompatActivity() {
    abstract val destination: Class<*>
    abstract val buttonText: String
    abstract val fondColor: Int

    private lateinit var binding: RunningAndShootingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RunningAndShootingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        binding.button.text = buttonText
        binding.runningpage.setBackgroundColor(fondColor)
        binding.button.setOnClickListener{
            finish()
            startActivity(Intent(this, destination))
        }
    }
}
package com.example.laserrun

import android.os.Bundle
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
        binding.category.text = categoryName
    }


}
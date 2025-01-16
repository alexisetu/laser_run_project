package com.example.laserrun

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.laserrun.databinding.CategoryChoiceBinding

class CategoryChoice : AppCompatActivity() {
    private lateinit var binding: CategoryChoiceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CategoryChoiceBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.annuler.setOnClickListener {
            finish()
        }
    }
}
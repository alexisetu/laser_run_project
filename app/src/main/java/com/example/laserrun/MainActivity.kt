package com.example.laserrun

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.laserrun.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Bouton Courir - Fonctionnel
        binding.courir.setOnClickListener {
            val intent = Intent(this, CategoryChoice::class.java)
            startActivity(intent)
        }

        // Bouton Historique - Toast
        binding.historiqueParties.setOnClickListener {
            Toast.makeText(this, "Fonctionnalité pas encore disponible", Toast.LENGTH_SHORT).show()
        }

        // Bouton Géolocalisation - Toast
        binding.geolocalisation.setOnClickListener {
            Toast.makeText(this, "Fonctionnalité pas encore disponible", Toast.LENGTH_SHORT).show()
        }

        // Bouton Statistiques - Toast
        binding.statistiques.setOnClickListener {
            Toast.makeText(this, "Fonctionnalité pas encore disponible", Toast.LENGTH_SHORT).show()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
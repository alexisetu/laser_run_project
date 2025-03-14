package com.example.laserrun

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.laserrun.databinding.CategoryChoiceBinding
import com.example.laserrun.service.ApiCategory
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import android.content.Intent

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
        getCategory()
    }

    private fun getCategory()
    {
        GlobalScope.launch(Dispatchers.Main)
        {
            try {
                val response = ApiCategory.apiService.getCategory()
                if (response.isSuccessful && response.body() != null) {
                    val categories = response.body()!!
                    val containerLayout = binding.buttonContainer

                    categories.forEach { category ->
                        val button = MaterialButton(this@CategoryChoice).apply {
                            layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            ).apply {
                                setMargins(60, 32, 60, 32)
                            }

                            val text = SpannableString(
                                getString(
                                    R.string.category_button_text,
                                    category.name.uppercase(),
                                    category.initialDistance,
                                    category.lapCount,
                                    category.lapDistance,
                                    category.shootDistance
                                )
                            )

                            text.setSpan(
                                AbsoluteSizeSpan(28, true),
                                0,
                                category.name.length,
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                            )
                            text.setSpan(
                                AbsoluteSizeSpan(10, true),
                                category.name.length,
                                text.length,
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                            )

                            setText(text)
                            textAlignment = android.view.View.TEXT_ALIGNMENT_CENTER

                            setPadding(140, 80, 140, 80)
                            setBackgroundColor(ContextCompat.getColor(context, R.color.boutonviolet))
                            cornerRadius = 60

                            setOnClickListener {
                                val intent = Intent(this@CategoryChoice, StartARun::class.java).apply {
                                    putExtra("categoryName", category.name)
                                    putExtra("INITIAL_DISTANCE", category.initialDistance.toString())
                                    putExtra("LAP_DISTANCE", category.lapDistance.toString())
                                    putExtra("SHOOT_DISTANCE", category.shootDistance.toString())
                                    putExtra("LAP_COUNT", category.lapCount)
                                    putExtra("CURRENT_LAP", 0)
                                }
                                startActivity(intent)
                            }

                        }
                        containerLayout.addView(button)
                    }
                }
                else
                {
                    Toast.makeText(
                        this@CategoryChoice,
                        "Error Occurred: ${response.message()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            catch (e : Exception)
            {
                Toast.makeText(
                    this@CategoryChoice,
                    "Error Occurred: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }

}
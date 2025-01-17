package com.example.laserrun.data

data class Category(
    val id: String,
    val name: String,
    val initialDistance: Int,
    val lapDistance: Int,
    val lapCount: Int,
    val shootDistance: Int
)

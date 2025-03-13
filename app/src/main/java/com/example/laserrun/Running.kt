package com.example.laserrun

import android.graphics.Color

class Running : RunningAndShooting() {
    override val destination: Class<*> = Shooting::class.java
    override val buttonText: String by lazy { getString(R.string.arrivee_stand) }
    override val fondColor: Int by lazy { getColor(R.color.fondvert) }
}
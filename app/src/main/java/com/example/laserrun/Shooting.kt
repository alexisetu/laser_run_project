package com.example.laserrun

import android.graphics.Color

class Shooting : RunningAndShooting() {
    override val destination: Class<*> = Running::class.java
    override val buttonText: String by lazy { getString(R.string.fivetarget) }
    override val fondColor: Int by lazy { getColor(R.color.fondviolet) }
}
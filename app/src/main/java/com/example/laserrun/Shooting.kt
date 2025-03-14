package com.example.laserrun

class Shooting : RunningAndShooting() {
    override val destination: Class<*> = Running::class.java
    override val buttonText: String by lazy { getString(R.string.cinqcibles) }
    override val fondColor: Int by lazy { getColor(R.color.fondviolet) }
    override val isLastLap: Boolean by lazy {
        false
    }
}
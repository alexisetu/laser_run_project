package com.example.laserrun

class Running : RunningAndShooting() {
    override val destination: Class<*> = Shooting::class.java
    override val buttonText: String by lazy { 
        if (isLastLap) {
            getString(R.string.fin_course)
        } else {
            getString(R.string.arrivee_stand)
        }
    }
    override val fondColor: Int by lazy { getColor(R.color.fondvert) }
    override val isLastLap: Boolean by lazy {
        val currentLap = intent.getIntExtra("CURRENT_LAP", 0)
        val lapCount = intent.getIntExtra("LAP_COUNT", 0)
        currentLap >= lapCount
    }
}
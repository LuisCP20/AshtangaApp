package com.example.ashtanga1.model

import androidx.annotation.DrawableRes

// Data class for each posture in the sequence
data class Asana(
    @DrawableRes val postureImageResourceId: Int,
    val name: String,
    val drishti: String,
    val bandha: String
)

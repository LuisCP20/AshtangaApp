package com.example.ashtanga1.data

import com.example.ashtanga1.R
import com.example.ashtanga1.model.Asana

object DataSource {

    val suryaA: List<Asana> = listOf(
        Asana(
        R.drawable.asanad,
        "Samashthitti",
        "dristhi1",
        "bandha1")
    )

    val suryaB: List<Asana> = listOf(
        Asana(
            R.drawable.asanad,
            "Samashthitti",
            "dristhi1",
            "bandha1")
    )

    val standingSequence: List<Asana> = listOf(
        Asana(
            R.drawable.asanad,
            "Samashthitti",
            "dristhi1",
            "bandha1")
    )

    val sittingSequence: List<Asana> = listOf(
        Asana(
            R.drawable.asanad,
            "Samashthitti",
            "dristhi1",
            "bandha1")
    )

    val finishingSequence: List<Asana> = listOf(
        Asana(
            R.drawable.asanad,
            "Samashthitti",
            "dristhi1",
            "bandha1")
    )

    val completeSequence: List<Asana> =
        suryaA + suryaB + standingSequence + sittingSequence + finishingSequence
}


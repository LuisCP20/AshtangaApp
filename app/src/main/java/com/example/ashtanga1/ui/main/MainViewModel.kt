package com.example.ashtanga1.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val mainMenu = listOf<String>("Posture", "Sequence", "Posture and Sequence","Review")
    val sequenceMenu = listOf<String>("Suryanamaskara A", "Suryanamaskara B", "Standing Sequence", "Primary Sequence", "Finishing Sequence", "Complete Series")
    val techniqueMenu = listOf<String>("Name", "Bandha", "Drishti", "Posture", "Random")

    // Selected mode
    private val _mode = MutableLiveData<String>()
    //Backing property
    val mode: LiveData<String> = _mode

    // Selected sequence
    private val _sequence = MutableLiveData<String>()
    val sequence: LiveData<String> = _sequence

    // Selected technique
    private val _technique = MutableLiveData<String>()
    val technique: LiveData<String> = _technique

    fun setMode(selectedMode: String){
        _mode.value = selectedMode
    }

    fun setSequence(selectedSequence: String){
        _sequence.value = selectedSequence
    }

    fun setTechnique(selectedTechnique: String){
        _technique.value = selectedTechnique
    }

}
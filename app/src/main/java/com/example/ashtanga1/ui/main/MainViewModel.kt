package com.example.ashtanga1.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ashtanga1.data.DataSource
import com.example.ashtanga1.model.Asana

class MainViewModel : ViewModel() {

    private val suryaA: List<Asana> = DataSource.suryaA
    private val suryaB: List<Asana> = DataSource.suryaB
    private val standingSequence: List<Asana> = DataSource.standingSequence
    private val sittingSequence: List<Asana> = DataSource.sittingSequence
    private val finishingSequence: List<Asana> = DataSource.finishingSequence
    private val completeSequence: List<Asana> = DataSource.completeSequence

    val sequencesData: List<List<Asana>> = listOf(suryaA,suryaB,standingSequence,
        sittingSequence,finishingSequence,completeSequence)

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

    // Length of sequence
    private val _seqLength = MutableLiveData<Int>()
    val seqLength: LiveData<Int> = _seqLength

    private val _score = MutableLiveData(0)
    val score: LiveData<Int>
        get() = _score
    private val _currentPosture = MutableLiveData(1)
    val currentPosture: LiveData<Int>
        get() = _currentPosture



    fun setMode(selectedMode: String){
        _mode.value = selectedMode
    }

    fun setSequence(selectedSequence: Int){
        _sequence.value = sequenceMenu[selectedSequence]
        _seqLength.value = sequencesData[selectedSequence].size
    }

    fun setTechnique(selectedTechnique: String){
        _technique.value = selectedTechnique
    }

}
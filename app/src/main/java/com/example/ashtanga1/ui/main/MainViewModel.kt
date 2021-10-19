package com.example.ashtanga1.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ashtanga1.data.DataSource
import com.example.ashtanga1.model.Asana
import kotlin.random.Random

class MainViewModel : ViewModel() {

    private val suryaA: List<Asana> = DataSource.suryaA
    private val suryaB: List<Asana> = DataSource.suryaB
    private val standingSequence: List<Asana> = DataSource.standingSequence
    private val sittingSequence: List<Asana> = DataSource.sittingSequence
    private val finishingSequence: List<Asana> = DataSource.finishingSequence
    private val completeSequence: List<Asana> = DataSource.completeSequence

    private val _sequencesData: List<List<Asana>> = listOf(suryaA,suryaB,standingSequence,
        sittingSequence,finishingSequence,completeSequence)
    val sequencesData: List<List<Asana>> = _sequencesData

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

    // Selected sequence index
    // TODO: ese 3 a algo mas decente
    private var _sequenceIndex: Int = 5
    private var _techniqueIndex: Int = 3
    var techniqueIndex = _techniqueIndex

    // Selected technique
    private val _technique = MutableLiveData<String>()
    val technique: LiveData<String> = _technique

    // Random technique

    private val _randomT = MutableLiveData<Boolean> (false)
    val randomT = _randomT

    // Length of sequence
    private val _seqLength = MutableLiveData<Int>(1)
    val seqLength: LiveData<Int>
        get() = _seqLength

    // Score
    private val _score = MutableLiveData<Int>(0)
    val score: LiveData<Int>
        get() = _score

    // Current position in sequence
    private val _sequencePosition: MutableLiveData<Int> = MutableLiveData(1)
    val sequencePosition: LiveData<Int>
        get() = _sequencePosition

    // Current posture
    private val _asana = MutableLiveData<Asana>()
    var asana = _asana

    // next posture
    private val _nextAsana = MutableLiveData<Asana>()
    var nextAsana = _nextAsana

    // Options
    private val _asanaOptions = MutableLiveData<List<Asana>>()
    var asanaOptions = _asanaOptions

    // Text options for buttons
    private val _textOptions = MutableLiveData<MutableList<String>>()
    var textOptions = _textOptions


    var finalScoreVar = ""
    //init{setAsana()}

    fun setMode(selectedMode: Int){
        _mode.value = mainMenu[selectedMode]
    }

    fun setSequence(selectedSequence: Int){
        _sequenceIndex = selectedSequence
        _sequence.value = sequenceMenu[selectedSequence]
        _seqLength.value = _sequencesData[selectedSequence].size
        if(_mode.value != mainMenu[0]){
            setAsana()
            setOptions()
        }
    }

    fun setTechnique(selectedTechnique: Int){
        _technique.value = techniqueMenu[selectedTechnique]
        _techniqueIndex = selectedTechnique
        setAsana()
        setOptions()
    }

    fun setRandomTech(activated:Boolean){
        _randomT.value = activated
    }

    fun correctAnswer(){
        _score.value = _score.value?.plus(1)
        _sequencePosition.value = _sequencePosition.value?.plus(1)
        if (_sequencePosition.value != _seqLength.value){
            Log.d("Test","Correct. Pos: ${_sequencePosition.value} Score: ${_score.value}")
            setAsana()
            setOptions()}
    }

    fun incorrectAnswer(){
        _sequencePosition.value = _sequencePosition.value?.plus(1)
        if (_sequencePosition.value != _seqLength.value){
            Log.d("Test","Incorrect. Pos: ${_sequencePosition.value} Score: ${_score.value}")
            setAsana()
            setOptions()}
    }

    fun correctAnswerTechnique(){
        // Se puede combinar con IncorrectAnswerTechnique, que reciba un bool llamarla con un .equals
        // comparando la elección con la respuesta correcta
        _score.value = _score.value?.plus(1)
        if (_sequencePosition.value != _seqLength.value){
            _sequencePosition.value = _sequencePosition.value?.plus(1)
            Log.d("Test","Correct. Pos: ${_sequencePosition.value} Score: ${_score.value}")
            setAsana()
            setOptions()}
        else{_sequencePosition.value = _sequencePosition.value?.plus(1)}
    }

    fun incorrectAnswerTechnique(){
        if (_sequencePosition.value != _seqLength.value){
            _sequencePosition.value = _sequencePosition.value?.plus(1)
            Log.d("Test","Incorrect. Pos: ${_sequencePosition.value} Score: ${_score.value}")
            setAsana()
            setOptions()}
        else{_sequencePosition.value = _sequencePosition.value?.plus(1)}
    }


    fun setAsana(){
        //sequenceMenu.indexOf(_sequencevalue)
        when(_sequence.value){
            sequenceMenu[0] -> asanaPicker(0,_sequencePosition.value!!)
            sequenceMenu[1] -> asanaPicker(1,_sequencePosition.value!!)
            sequenceMenu[2] -> asanaPicker(2,_sequencePosition.value!!)
            sequenceMenu[3] -> asanaPicker(3,_sequencePosition.value!!)
            sequenceMenu[4] -> asanaPicker(4,_sequencePosition.value!!)
            sequenceMenu[5] -> asanaPicker(5,_sequencePosition.value!!)
        }
    }

    fun asanaPicker(sequence: Int, position: Int){
        _asana.value = _sequencesData[sequence][position-1]
        if(_mode.value != mainMenu[0]){
            _nextAsana.value = _sequencesData[sequence][position]}
    }

    // Assign four different postures, one being the correct one.
    fun setOptions(){
        when (_mode.value){
            mainMenu[0] -> optionsList(true)
            mainMenu[1] -> optionsList(false)
            mainMenu[2] -> optionsList(true)
        }
    }

    // Create list of options including correct answer.
    fun optionsList(includeCurrent: Boolean){
        if (includeCurrent){
            // TODO: los ceros son surya A, cambiarlo para que varie con cada secuencia. Revisar que sirva en las otras
                // TODO: Que no salgan repetidas, pasa porque hay repetidas en las secuencias
            var options =mutableListOf(_sequencesData[_sequenceIndex][_sequencePosition.value!!-1]// Correct answer
                , _sequencesData[_sequenceIndex][Random.nextInt(0,_sequencesData[_sequenceIndex].size)]
                , _sequencesData[_sequenceIndex][Random.nextInt(0,_sequencesData[_sequenceIndex].size)]
                , _sequencesData[_sequenceIndex][Random.nextInt(0,_sequencesData[_sequenceIndex].size)])

            for(i in 1..3){ // Add wrong answers
                while(options.count{it == options[i]} > 1){
                    options[i] = _sequencesData[_sequenceIndex][Random.nextInt(0,_sequencesData[_sequenceIndex].size)]
                }
            }
            options.shuffle()
            _asanaOptions.value = options
            setTextOptions(options)
        } else {
            var options =mutableListOf(_sequencesData[_sequenceIndex][_sequencePosition.value!!]// Correct answer
                , _sequencesData[_sequenceIndex][Random.nextInt(0,_sequencesData[_sequenceIndex].size)]
                , _sequencesData[_sequenceIndex][Random.nextInt(0,_sequencesData[_sequenceIndex].size)]
                , _sequencesData[_sequenceIndex][Random.nextInt(0,_sequencesData[_sequenceIndex].size)])

            for(i in 1..3){ // Add wrong answers
                while(options.count{it == options[i]} > 1 || options[i].name == _sequencesData[_sequenceIndex][_sequencePosition.value!!-1].name){
                    options[i] = _sequencesData[_sequenceIndex][Random.nextInt(0,_sequencesData[_sequenceIndex].size.minus(1))]
                }
            }
            options.shuffle()
            _asanaOptions.value = options
            setTextOptions(options)
        }
    }

    fun setTextOptions(options: List<Asana>){
        //Log.d("Test", "TechniqueIndex:${_techniqueIndex.toString()}")
        when(_techniqueIndex){
//            0-> _textOptions.value = mutableListOf(options[0].name, options[1].name,
//                options[2].name, options[3].name)
            1-> _textOptions.value = mutableListOf(options[0].bandha, options[1].bandha,
                options[2].bandha, options[3].bandha)
            2-> _textOptions.value = mutableListOf(options[0].drishti, options[1].drishti,
                options[2].drishti, options[3].drishti)
            3 ->_textOptions.value = mutableListOf(options[0].drishti, options[1].drishti,
                options[2].drishti, options[3].drishti)
            else-> _textOptions.value = mutableListOf(options[0].name, options[1].name,
                options[2].name, options[3].name)
        }
    }

    fun finalScoreString():String{
        var finalScore: String = ""
        when (_mode.value){
            mainMenu[0] -> finalScore = "${_score.value}/${_seqLength.value}"
            mainMenu[1] -> finalScore ="${_score.value}/${_seqLength.value?.minus(1)}"
            mainMenu[2] -> finalScore ="${_score.value}/${_seqLength.value?.minus(1)}"
        }
        return finalScore
    }
    fun reset(){
        _score.value = 0
        _sequencePosition.value = 1
        _sequenceIndex = 5
        _randomT.value = false
        finalScoreVar = "0"
    }

}
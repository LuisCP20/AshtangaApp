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

    private val _sequencesData: List<List<Asana>> = listOf(
        suryaA, suryaB, standingSequence,
        sittingSequence, finishingSequence, completeSequence
    )
    val sequencesData: List<List<Asana>> = _sequencesData

    val mainMenu = listOf<String>("Posture", "Sequence", "Posture and Sequence", "Review")
    private val sequenceMenu = listOf<String>(
        "Suryanamaskara A",
        "Suryanamaskara B",
        "Standing Sequence",
        "Primary Sequence",
        "Finishing Sequence",
        "Complete Series"
    )
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
    private val _randomT = MutableLiveData<Boolean>(false)
    val randomT = _randomT

    // Sequence and posture
    private val _combined = MutableLiveData<Boolean>(false)
    val combined = _combined
    private var bothAnswered = false // Advance to next Asana in combined mode

    // Length of sequence
    private val _seqLength = MutableLiveData<Int>(1)
    val seqLength: LiveData<Int>
        get() = _seqLength

    // Length of sequence
    private val _posCounter = MutableLiveData<Int>(1)
    val posCounter: LiveData<Int>
        get() = _posCounter

    // Score
    private val _score = MutableLiveData<Int>(0)
    val score: LiveData<Int>
        get() = _score

    // Current position in sequence
    private val _sequencePosition: MutableLiveData<Int> = MutableLiveData(1)
    val sequencePosition: LiveData<Int>
        get() = _sequencePosition

    // Current question
    private val _questionPosition: MutableLiveData<Int> = MutableLiveData(1)
    val questionPosition: LiveData<Int>
        get() = _questionPosition

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

    fun setMode(selectedMode: Int) {
        _mode.value = mainMenu[selectedMode]
    }

    fun setSequence(selectedSequence: Int) {
        _sequenceIndex = selectedSequence
        _sequence.value = sequenceMenu[selectedSequence]
        if (combined.value == true) {
            _seqLength.value = 2 * _sequencesData[selectedSequence].size // 2 questions per Asana
        } else {
            _seqLength.value = _sequencesData[selectedSequence].size
        }// One Q per asana

        if (_mode.value != mainMenu[0]) {
            setAsana()
            setOptions()
        }

        when (_mode.value) {
            mainMenu[0] -> _posCounter.value = _seqLength.value
            mainMenu[1] -> _posCounter.value = _seqLength.value?.minus(1)
            mainMenu[2] -> _posCounter.value = _seqLength.value?.minus(1)
            mainMenu[3] -> _posCounter.value = 2 * _seqLength.value!! - 1
        }
    }

    fun setTechnique(selectedTechnique: Int) {
        _technique.value = techniqueMenu[selectedTechnique]
        _techniqueIndex = selectedTechnique
        setAsana()
        setOptions()
    }

    fun setRandomTech(activated: Boolean) {
        _randomT.value = activated
    }

    fun setCombinedMode(activated: Boolean) {
        _combined.value = true
    }

    fun clearText() {
        _textOptions.value = mutableListOf<String>("", "", "", "")
    }

    fun correctAnswer() {
        _score.value = _score.value?.plus(1)
        advance()
        if (_questionPosition.value != _seqLength.value) {
            Log.d("Test", "Correct. Pos: ${_questionPosition.value} Score: ${_score.value}")
            setAsana()
            setOptions()
        }
    }

    fun incorrectAnswer() {
        advance()
        if (_questionPosition.value != _seqLength.value) {
            Log.d("Test", "Incorrect. Pos: ${_questionPosition.value} Score: ${_score.value}")
            setAsana()
            setOptions()
        }
    }

    private fun advance() {
        Log.d("Test", "Load next asana: ${bothAnswered} Combined: ${combined.value}")
        if (_combined.value == true) { // Progression in combined mode
            if (bothAnswered) {
                _sequencePosition.value = _sequencePosition.value?.plus(1)
            }
            bothAnswered = !bothAnswered
            _questionPosition.value = _questionPosition.value?.plus(1)
        } else {
            _questionPosition.value = _questionPosition.value?.plus(1)
            _sequencePosition.value = _sequencePosition.value?.plus(1)
        }
    }

    fun correctAnswerTechnique() {
        // Se puede combinar con IncorrectAnswerTechnique, que reciba un bool llamarla con un .equals
        // comparando la elección con la respuesta correcta
        _score.value = _score.value?.plus(1)
        if (_questionPosition.value != _seqLength.value) {
            advance()
            Log.d("Correct", "Correct. Pos: ${_questionPosition.value} Score: ${_score.value}")
            setAsana()
            setOptions()
        } else {
            advance()
        }
    }

    fun incorrectAnswerTechnique() {
        if (_questionPosition.value != _seqLength.value) {
            advance()
            Log.d("Correct", "Incorrect. Pos: ${_questionPosition.value} Score: ${_score.value}")
            setAsana()
            setOptions()
        } else {
            advance()
        }
    }


    private fun setAsana() {
        //sequenceMenu.indexOf(_sequencevalue)
        when (_sequence.value) {
            sequenceMenu[0] -> asanaPicker(0, _sequencePosition.value!!)
            sequenceMenu[1] -> asanaPicker(1, _sequencePosition.value!!)
            sequenceMenu[2] -> asanaPicker(2, _sequencePosition.value!!)
            sequenceMenu[3] -> asanaPicker(3, _sequencePosition.value!!)
            sequenceMenu[4] -> asanaPicker(4, _sequencePosition.value!!)
            sequenceMenu[5] -> asanaPicker(5, _sequencePosition.value!!)
        }
    }

    private fun asanaPicker(sequence: Int, position: Int) {
        _asana.value = _sequencesData[sequence][position - 1]
        if (position < _sequencesData[sequence].size) {
            _nextAsana.value = _sequencesData[sequence][position]
        }
    }

    // Assign four different postures, one being the correct one.
    private fun setOptions() {
        when (_mode.value) {
            mainMenu[0] -> optionsList(true)
            mainMenu[1] -> optionsList(false)
            mainMenu[2] -> optionsList(true)
        }
    }

    // Create list of options including correct answer.
    private fun optionsList(includeCurrent: Boolean) {
        if (includeCurrent && !bothAnswered) {
            // TODO: los ceros son surya A, cambiarlo para que varie con cada secuencia. Revisar que sirva en las otras
            // TODO: Que no salgan repetidas, pasa porque hay repetidas en las secuencias
            var options = mutableListOf(
                _sequencesData[_sequenceIndex][_sequencePosition.value!! - 1]// Correct answer
                ,
                _sequencesData[_sequenceIndex][Random.nextInt(
                    0,
                    _sequencesData[_sequenceIndex].size
                )],
                _sequencesData[_sequenceIndex][Random.nextInt(
                    0,
                    _sequencesData[_sequenceIndex].size
                )],
                _sequencesData[_sequenceIndex][Random.nextInt(
                    0,
                    _sequencesData[_sequenceIndex].size
                )]
            )
            while (options.distinct().size < options.size) {
                for (i in 1..3) { // Add wrong answers
                    options[i] = _sequencesData[_sequenceIndex][Random.nextInt(
                        0,
                        _sequencesData[_sequenceIndex].size
                    )]
                }
            }
            options.shuffle()
            _asanaOptions.value = options
            if (_combined.value == true && bothAnswered) {
                clearText()
            } else
                setTextOptions(options)
        } else {
            val rightAnswer = _sequencePosition.value ?: 0
            if (rightAnswer < _sequencesData[_sequenceIndex].size) { // Check if last
                var options = mutableListOf(
                    _sequencesData[_sequenceIndex][rightAnswer]// Correct answer
                    ,
                    _sequencesData[_sequenceIndex][Random.nextInt(
                        0,
                        _sequencesData[_sequenceIndex].size
                    )],
                    _sequencesData[_sequenceIndex][Random.nextInt(
                        0,
                        _sequencesData[_sequenceIndex].size
                    )],
                    _sequencesData[_sequenceIndex][Random.nextInt(
                        0,
                        _sequencesData[_sequenceIndex].size
                    )]
                )
                Log.d("Correct", "Answer:${options[0].name}")
                while (options.distinct().size < options.size || options.contains(_asana.value)) {
                    for (i in 1..3) { // Add wrong answers
                        options[i] = _sequencesData[_sequenceIndex][Random.nextInt(
                            0,
                            _sequencesData[_sequenceIndex].size
                        )]
                    }
                }
                Log.d(
                    "Repeat",
                    "After:${options[0].name}${options[1].name}${options[2].name}${options[3].name}"
                )
                options.shuffle()
                _asanaOptions.value = options
                if (_combined.value == true && bothAnswered) {
                    Log.d("NoText", "Combined:${combined.value},Mode: ${_mode.value}")
                    clearText()
                } else
                    setTextOptions(options)
            }
        }
    }

    private fun setTextOptions(options: List<Asana>) {
        //Log.d("Test", "TechniqueIndex:${_techniqueIndex.toString()}")

        when (_techniqueIndex) {
//            0-> _textOptions.value = mutableListOf(options[0].name, options[1].name,
//                options[2].name, options[3].name)
            1 -> _textOptions.value = mutableListOf(
                options[0].bandha, options[1].bandha,
                options[2].bandha, options[3].bandha
            )
            2 -> _textOptions.value = mutableListOf(
                options[0].drishti, options[1].drishti,
                options[2].drishti, options[3].drishti
            )
            3 -> _textOptions.value = mutableListOf(
                options[0].drishti, options[1].drishti,
                options[2].drishti, options[3].drishti
            )
            else -> _textOptions.value = mutableListOf(
                options[0].name, options[1].name,
                options[2].name, options[3].name
            )
        }
        if (_mode.value == mainMenu[1]) {
            _textOptions.value = mutableListOf(
                options[0].name, options[1].name,
                options[2].name, options[3].name
            )
        }
    }

    fun finalScoreString(): String {
        return "${_score.value}/${posCounter.value.toString()}"
    }

    fun reset() {
        _score.value = 0
        _questionPosition.value = 1
        _sequencePosition.value = 1
        _sequenceIndex = 5
        _randomT.value = false
        _combined.value = false
        bothAnswered = false
        finalScoreVar = "0"
    }

}
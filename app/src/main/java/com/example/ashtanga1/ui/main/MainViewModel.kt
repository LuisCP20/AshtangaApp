package com.example.ashtanga1.ui.main

import android.content.Context
import android.util.Log
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ashtanga1.data.DataSource
import com.example.ashtanga1.model.Asana
import java.util.*
import kotlin.random.Random

// TODO: Mejorar UI, que todo funcione bien, enseñar score y/o errores
// TODO: Bug cuando uso el boton de exit mientras esta cambiando el color del fondo para respuesta.
// TODO: Arreglarlo de una manera que no sea desactivar el boton de exit
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
    private val drishtis = listOf<String>(
        "Broomadhya Drishti",
        "Hastagrai Drishti",
        "Nasagrai Drishti",
        "Padhayoragai Drishti",
        "Parsva Drishti",
        "Urdhva Drishti"
    )
    val mainMenu = listOf("Posture", "Sequence", "Posture and Sequence", "Review", "Practice")
    val sequenceMenu = listOf(
        "Suryanamaskara A",
        "Suryanamaskara B",
        "Standing Sequence",
        "Primary Sequence",
        "Finishing Sequence",
        "Complete Series"
    )

    val techniqueMenu = listOf("Name", "Bandha", "Drishti", "Posture", "Random")

    // Selected mode
    private val _mode = MutableLiveData<String>()

    //Backing property
    val mode: LiveData<String> = _mode

    // Selected sequence
    private val _sequence = MutableLiveData<String>()

    // Selected sequence index
    private var _sequenceIndex: Int = 0
    private var _techniqueIndex: Int = 0

    // Selected technique
    private val _technique = MutableLiveData<String>()
    val technique: LiveData<String> = _technique

    // Random technique
    private val _randomT = MutableLiveData(false)
    val randomT = _randomT

    // Sequence and posture
    private val _combined = MutableLiveData(false)
    val combined = _combined
    private var bothAnswered = false // Advance to next Asana in combined mode

    // Length of sequence
    private val _seqLength = MutableLiveData(1)
    val seqLength: LiveData<Int>
        get() = _seqLength

    // Length of sequence
    private val _posCounter = MutableLiveData(1)
    val posCounter: LiveData<Int>
        get() = _posCounter

    // Score
    private val _score = MutableLiveData(0)
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

    // Status of the buttons
    private val _buttons = MutableLiveData(true)
    var buttons = _buttons


    var finalScoreVar = ""

    // Set current mode from main menu
    fun setMode(selectedMode: Int) {
        _mode.value = mainMenu[selectedMode]
    }

    // Button enable/disable
    fun enableButtons(enable: Boolean) {
        _buttons.value = enable
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

    fun setCombinedMode() {
        _combined.value = true
    }

    // Set the selected sequence from sequence menu
    fun setSequence(selectedSequence: Int) {
        _sequenceIndex = selectedSequence
        _sequence.value = sequenceMenu[selectedSequence]

        // In combined mode there are 2 questions per posture
        if (combined.value == true) {
            _seqLength.value = 2 * _sequencesData[selectedSequence].size // 2 questions per Asana
        } else { // One Q per asana
            _seqLength.value = _sequencesData[selectedSequence].size
        }

        // Mode 0 still has to select technique
        if (_mode.value != mainMenu[0]) {
            setAsana()
            setOptions()
        }

        // Set counter to display as user progresses through the series
        when (_mode.value) { //
            mainMenu[0] -> _posCounter.value = _seqLength.value
            mainMenu[1] -> _posCounter.value = _seqLength.value?.minus(1)
            mainMenu[2] -> _posCounter.value = _seqLength.value?.minus(1)
            mainMenu[3] -> _posCounter.value = 2 * _seqLength.value!! - 1
        }
    }

    // Used to hide names of postures
    private fun clearText() {
        _textOptions.value = mutableListOf("", "", "", "")
    }

    // Add 1 to score when correct then pick next posture
    fun correctAnswer() {
        _score.value = _score.value?.plus(1)
        advance()
        if (_questionPosition.value != _seqLength.value) {
            Log.d("Test", "Correct. Pos: ${_questionPosition.value} Score: ${_score.value}")
            setAsana()
            setOptions()
        }
    }

    // Don't 1 to score when correct then pick next posture
    fun incorrectAnswer() {
        advance()
        if (_questionPosition.value != _seqLength.value) {
            Log.d("Test", "Incorrect. Pos: ${_questionPosition.value} Score: ${_score.value}")
            setAsana()
            setOptions()
        }
    }

    // When possible move position and question counters one position
    private fun advance() {
        Log.d("Test", "Load next asana: ${bothAnswered} Combined: ${combined.value}")
        if (_combined.value == true) { // Progression in combined mode
            if (bothAnswered) { // Only advance after 2 questions have been answered per posture
                _sequencePosition.value = _sequencePosition.value?.plus(1)
            }
            bothAnswered = !bothAnswered
            _questionPosition.value =
                _questionPosition.value?.plus(1) // Question counter always advances
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

    fun back() {
        _sequencePosition.value = _sequencePosition.value?.minus(1)
        _questionPosition.value = _questionPosition.value?.minus(1)
        // If _asana.value is not in the list the index will be -1
        _asana.value =
            _sequencesData[sequenceMenu.indexOf(_sequence.value)][_sequencePosition.value!! - 1]

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
//            while (options.distinct().size < options.size) {
//                for (i in 1..3) { // Add wrong answers
//                    options[i] = _sequencesData[_sequenceIndex][Random.nextInt(
//                        0,
//                        _sequencesData[_sequenceIndex].size
//                    )]
//                }
//            }
            options = reRoll(options, includeCurrent)
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
//                while (options.distinct().size < options.size || options.contains(_asana.value)) {
//                    for (i in 1..3) { // Add wrong answers
//                        options[i] = _sequencesData[_sequenceIndex][Random.nextInt(
//                            0,
//                            _sequencesData[_sequenceIndex].size
//                        )]
//                    }
//                }
                options = reRoll(options, includeCurrent)
                Log.d(
                    "Repeat",
                    "After:${options[0].name}${options[1].name}${options[2].name}${options[3].name}"
                )

                // TODO: Asegurarme que en Drishti no salgan repetidas
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

    private fun reRoll(options: MutableList<Asana>, includeCurrent: Boolean): MutableList<Asana> {
        if (includeCurrent) {
            if (_technique.value == techniqueMenu[2]) { // Only for drishti
                val optionsDrishti = mutableListOf<String>()
                for (i in 0 until options.size - 1) {
                    optionsDrishti.add(options[i].drishti)
                }
                while (optionsDrishti.distinct().size < optionsDrishti.size || optionsDrishti.contains(
                        _asana.value?.drishti
                    )
                ) {
                    for (i in 1..3) { // Add wrong answers
                        options[i] = _sequencesData[_sequencesData.size - 1][Random.nextInt(
                            0,
                            _sequencesData[_sequencesData.size - 1].size
                        )]
                        optionsDrishti[i - 1] = options[i].drishti
                    }
                    Log.d("Correct", "Drishtis:${optionsDrishti}")
                }
            } else {
                while (options.distinct().size < options.size) {
                    for (i in 1..3) { // Add wrong answers
                        options[i] = _sequencesData[_sequenceIndex][Random.nextInt(
                            0,
                            _sequencesData[_sequenceIndex].size
                        )]
                    }
                }
            }
        } else {
            while (options.distinct().size < options.size || options.contains(_asana.value)) {
                for (i in 1..3) { // Add wrong answers
                    options[i] = _sequencesData[_sequenceIndex][Random.nextInt(
                        0,
                        _sequencesData[_sequenceIndex].size
                    )]
                }
            }
        }
        return options
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
        _posCounter.value = 0
        _seqLength.value = 0
        _questionPosition.value = 1
        _sequencePosition.value = 1
        _sequenceIndex = 5
        _randomT.value = false
        _combined.value = false
        bothAnswered = false
        finalScoreVar = "0"
        _buttons.value = true
    }

}
package com.example.ashtanga1.ui.main

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.ashtanga1.databinding.FragmentSequenceQuestionBinding
import java.util.*
import kotlin.concurrent.thread
import kotlin.random.Random
import android.R
import android.widget.Button
import android.widget.ImageButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll


class SequenceQuestionFragment : Fragment() {

    private val sharedViewModel: MainViewModel by activityViewModels()
    private var binding: FragmentSequenceQuestionBinding? = null
    private val delayTime = 200L // Delay in ms for changing colors between questions

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmentBinding = FragmentSequenceQuestionBinding.inflate(inflater, container,false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            fragmentSequenceQuestion = this@SequenceQuestionFragment
        }
    }

    fun checkAnswer(imageId: Int){
        val view =  view?.rootView
        val handle = Handler()
        // TODO: revisar el repeat que no funciona
        // TODO: mejorar colores
        // TODO: enseñar respuesta
        sharedViewModel.enableButtons(false)
        if(imageId == sharedViewModel.nextAsana.value?.postureImageResourceId){
            repeat(3){
                view?.setBackgroundResource(R.color.holo_green_dark)
                view?.postDelayed({view?.setBackgroundResource(R.color.white)},delayTime)
            }
            handle.postDelayed({
                sharedViewModel.correctAnswer()
                if(sharedViewModel.questionPosition.value == sharedViewModel.seqLength.value){
                    sharedViewModel.finalScoreVar = sharedViewModel.finalScoreString()
                    findNavController().navigate(com.example.ashtanga1.R.id.action_sequenceQuestionFragment_to_finishedFragment2)
                } else{
                    if(sharedViewModel.combined.value == true){navigateNextScreen()}
                }
                sharedViewModel.enableButtons(true)},3*delayTime)
        }
        else{
            repeat(3){
                view?.setBackgroundResource(R.color.holo_red_dark)
                view?.postDelayed({view?.setBackgroundResource(R.color.white)},delayTime)
            }
            handle.postDelayed({
                sharedViewModel.incorrectAnswer()
                if(sharedViewModel.questionPosition.value == sharedViewModel.seqLength.value){
                    sharedViewModel.finalScoreVar = sharedViewModel.finalScoreString()
                    findNavController().navigate(com.example.ashtanga1.R.id.action_sequenceQuestionFragment_to_finishedFragment2)
                }else{
                    if(sharedViewModel.combined.value == true){navigateNextScreen()}
                }
                sharedViewModel.enableButtons(true)}, 3*delayTime)
        }
    }


    private fun navigateNextScreen(){
        val techniques = listOf(0,2) // technique 0: Name technique 2: Drishti
        val selected = techniques[Random.nextInt(0,techniques.size)]
        Log.d("RandomTech", "NextQ${selected}")
        sharedViewModel.setTechnique(selected)
        findNavController().navigate(com.example.ashtanga1.R.id.action_sequenceQuestionFragment_to_techniqueQuestionFragment)
    }

    fun exit(){
        sharedViewModel.reset()
        findNavController().navigate(com.example.ashtanga1.R.id.action_sequenceQuestionFragment_to_mainFragment)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}
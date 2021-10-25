package com.example.ashtanga1.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.ashtanga1.R
import com.example.ashtanga1.databinding.FragmentTechniqueQuestionBinding
import com.example.ashtanga1.model.Asana
import kotlin.random.Random

class TechniqueQuestionFragment : Fragment() {

    private val sharedViewModel: MainViewModel by activityViewModels()
    private var binding: FragmentTechniqueQuestionBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmentBinding = FragmentTechniqueQuestionBinding.inflate(inflater, container,false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            fragmentTechniqueQuestion = this@TechniqueQuestionFragment
        }
    }

    fun checkAnswer(selection: Asana){
        Log.d("Test", "${selection}, ${sharedViewModel.asana.value}")
        // Comparar nombres deberia funcionar con Drishti y con imagenes
        if(selection.postureImageResourceId == (sharedViewModel.asana.value?.postureImageResourceId)){ // Comparo con imagenes, ya que nombres pueden variar (ABC)
            sharedViewModel.correctAnswerTechnique()
            checkLast()
        }
        else{
            sharedViewModel.incorrectAnswerTechnique()
            checkLast()
        }

    }

    // Check if current posture was the last one
    private fun checkLast(){

        // Change last question limit depending of mode
        var limit = sharedViewModel.seqLength.value?.plus(1)
        if(sharedViewModel.combined.value == true){
            limit = sharedViewModel.seqLength.value}

        if(sharedViewModel.questionPosition.value == limit){
            sharedViewModel.finalScoreVar = sharedViewModel.finalScoreString()
            findNavController().navigate(R.id.action_techniqueQuestionFragment_to_finishedFragment2)
        } else {
            if (sharedViewModel.combined.value == true){
                findNavController().navigate(R.id.action_techniqueQuestionFragment_to_sequenceQuestionFragment)
            }
            else if(sharedViewModel.randomT.value == true){
                val nextTech = randomTechnique()
                if (nextTech == 3){ findNavController().navigate(R.id.action_techniqueQuestionFragment_to_postureQuestionFragment2) }
            }
        }
    }

    private fun randomTechnique() :Int{
        val techniques = listOf(0,2,3)
        val selected = techniques[Random.nextInt(0,techniques.size)]
        Log.d("RandomTech", "NextQ${selected}")
        sharedViewModel.setTechnique(selected)
        return selected
    }

    fun exit(){
        sharedViewModel.reset()
        findNavController().navigate(R.id.action_techniqueQuestionFragment_to_mainFragment)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}
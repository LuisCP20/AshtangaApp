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
import com.example.ashtanga1.databinding.FragmentSequenceQuestionBinding
import kotlin.random.Random

class SequenceQuestionFragment : Fragment() {

    private val sharedViewModel: MainViewModel by activityViewModels()
    private var binding: FragmentSequenceQuestionBinding? = null

    // 4 options per asana

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
        if(imageId == sharedViewModel.nextAsana.value?.postureImageResourceId){
            sharedViewModel.correctAnswer()
            if(sharedViewModel.questionPosition.value == sharedViewModel.seqLength.value){
                sharedViewModel.finalScoreVar = sharedViewModel.finalScoreString()
                findNavController().navigate(R.id.action_sequenceQuestionFragment_to_finishedFragment2)
            } else{
                if(sharedViewModel.combined.value == true){navigateNextScreen()}
            }
        }
        else{
            sharedViewModel.incorrectAnswer()
            if(sharedViewModel.questionPosition.value == sharedViewModel.seqLength.value){
                sharedViewModel.finalScoreVar = sharedViewModel.finalScoreString()
                findNavController().navigate(R.id.action_sequenceQuestionFragment_to_finishedFragment2)
            }else{
                if(sharedViewModel.combined.value == true){navigateNextScreen()}
            }
        }
    }


    private fun navigateNextScreen(){
        val techniques = listOf<Int>(0,2,3)
        val selected = techniques[Random.nextInt(0,techniques.size)]
        Log.d("RandomTech", "NextQ${selected}")
        sharedViewModel.setTechnique(selected)
        when(selected){
            techniques[2] -> findNavController().navigate(R.id.action_sequenceQuestionFragment_to_postureQuestionFragment2)
            else -> findNavController().navigate(R.id.action_sequenceQuestionFragment_to_techniqueQuestionFragment)
        }
    }

    fun exit(){
        sharedViewModel.reset()
        findNavController().navigate(R.id.action_sequenceQuestionFragment_to_mainFragment)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
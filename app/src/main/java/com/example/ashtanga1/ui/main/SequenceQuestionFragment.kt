package com.example.ashtanga1.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.ashtanga1.R
import com.example.ashtanga1.databinding.FragmentSequenceQuestionBinding

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
            if(sharedViewModel.sequencePosition.value?.plus(1) == sharedViewModel.seqLength.value){
                findNavController().navigate(R.id.action_sequenceQuestionFragment_to_finishedFragment2)
            }
        }
        else{
            sharedViewModel.incorrectAnswer()
            if(sharedViewModel.sequencePosition.value?.plus(1) == sharedViewModel.seqLength.value){
                findNavController().navigate(R.id.action_sequenceQuestionFragment_to_finishedFragment2)
            }
        }
        //findNavController().navigate(R.id.action_sequenceQuestionFragment_to_finishedFragment2)
    }



    fun exit(){
        // TODO: Volver al inicio, resetear variables y borrar el backstack

    }
}
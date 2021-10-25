package com.example.ashtanga1.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.ashtanga1.R
import com.example.ashtanga1.databinding.FragmentSequenceMenuBinding


class SequenceMenuFragment : Fragment() {

    private val sharedViewModel: MainViewModel by activityViewModels()
    private var binding: FragmentSequenceMenuBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmentBinding = FragmentSequenceMenuBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            fragmentSequenceMenu = this@SequenceMenuFragment
        }
    }

    fun goToNextScreen(sequence: Int){
        sharedViewModel.setSequence(sequence)
        selectNextScreen()
    }

    private fun selectNextScreen(){
        when (sharedViewModel.mode.value.toString()){
            sharedViewModel.mainMenu[0] -> findNavController().navigate(R.id.action_sequenceMenuFragment2_to_techniqueMenuFragment)
            sharedViewModel.mainMenu[1] -> findNavController().navigate(R.id.action_sequenceMenuFragment2_to_sequenceQuestionFragment)
            sharedViewModel.mainMenu[2] -> findNavController().navigate(R.id.action_sequenceMenuFragment2_to_techniqueQuestionFragment)
            else -> exit()
            // TODO: revisar otros casos
        }
    }
    
    fun exit(){
        sharedViewModel.reset()
        findNavController().navigate(R.id.action_techniqueQuestionFragment_to_mainFragment)

    }
}
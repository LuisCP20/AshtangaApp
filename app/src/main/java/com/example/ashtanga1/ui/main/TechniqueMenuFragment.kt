package com.example.ashtanga1.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.ashtanga1.R
import com.example.ashtanga1.databinding.FragmentTechniqueMenuBinding
import kotlin.random.Random

class TechniqueMenuFragment : Fragment() {

    private val sharedViewModel: MainViewModel by activityViewModels()
    private var binding: FragmentTechniqueMenuBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmentBinding = FragmentTechniqueMenuBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            fragmentTechniqueMenu= this@TechniqueMenuFragment
        }
    }

    fun goToNextScreen(technique: Int){
        sharedViewModel.setTechnique(technique)
        selectNextScreen()
    }

    private fun selectNextScreen(){
        when (sharedViewModel.technique.value.toString()){
            // Name
            sharedViewModel.techniqueMenu[0] -> findNavController().navigate(R.id.action_techniqueMenuFragment_to_techniqueQuestionFragment)
            // Bandha
            sharedViewModel.techniqueMenu[1] -> findNavController().navigate(R.id.action_techniqueMenuFragment_to_techniqueQuestionFragment)
            // Drishti
            sharedViewModel.techniqueMenu[2] -> findNavController().navigate(R.id.action_techniqueMenuFragment_to_techniqueQuestionFragment)
            // Posture
            sharedViewModel.techniqueMenu[3] -> findNavController().navigate(R.id.action_techniqueMenuFragment_to_postureQuestionFragment2)

            else -> findNavController().navigate(R.id.action_techniqueMenuFragment_to_postureQuestionFragment2) // TODO: revisar otros casos
        }
    }

    fun randomTechnique(): Int{
        val techniques = listOf(0,2,3)
        sharedViewModel.setRandomTech(true)
        return techniques[Random.nextInt(0,techniques.size)]
    }

    fun exit(){
        val currentMode = sharedViewModel.mainMenu.indexOf(sharedViewModel.mode.value)
        sharedViewModel.reset()
        sharedViewModel.setMode(currentMode)
        findNavController().navigate(R.id.action_techniqueMenuFragment_to_sequenceMenuFragment2)
    }

}
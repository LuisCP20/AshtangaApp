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

/**
 * A simple [Fragment] subclass.
 * Use the [TechniqueMenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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
            sharedViewModel.techniqueMenu[0] -> findNavController().navigate(R.id.action_techniqueMenuFragment_to_techniqueQuestionFragment)
            sharedViewModel.techniqueMenu[1] -> findNavController().navigate(R.id.action_techniqueMenuFragment_to_techniqueQuestionFragment)
            sharedViewModel.techniqueMenu[2] -> findNavController().navigate(R.id.action_techniqueMenuFragment_to_techniqueQuestionFragment)
            sharedViewModel.techniqueMenu[3] -> findNavController().navigate(R.id.action_techniqueMenuFragment_to_postureQuestionFragment2)
            sharedViewModel.techniqueMenu[4] -> findNavController().navigate(R.id.action_techniqueMenuFragment_to_postureQuestionFragment2)
            else -> findNavController().navigate(R.id.action_techniqueMenuFragment_to_postureQuestionFragment2)
            // TODO: revisar otros casos
        }
    }

}
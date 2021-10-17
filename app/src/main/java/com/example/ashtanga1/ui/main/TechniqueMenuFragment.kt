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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TechniqueMenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TechniqueMenuFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val sharedViewModel: MainViewModel by activityViewModels()
    private var binding: FragmentTechniqueMenuBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

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


    fun goToNextScreen(){
        findNavController().navigate(R.id.action_techniqueMenuFragment_to_techniqueQuestionFragment)
    }

    fun goToNextScreen(technique: Int){
        when (technique){
            0 -> sharedViewModel.setTechnique(sharedViewModel.techniqueMenu[0])
            1 -> sharedViewModel.setTechnique(sharedViewModel.techniqueMenu[1])
            2 -> sharedViewModel.setTechnique(sharedViewModel.techniqueMenu[2])
            3 -> sharedViewModel.setTechnique(sharedViewModel.techniqueMenu[3])
            4 -> sharedViewModel.setTechnique(sharedViewModel.techniqueMenu[4])
        }
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TechniqueMenuFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TechniqueMenuFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
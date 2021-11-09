package com.example.ashtanga1.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.ashtanga1.R
import com.example.ashtanga1.databinding.FragmentPracticeBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class PracticeFragment : Fragment() {

    private val sharedViewModel: MainViewModel by activityViewModels()
    private var binding: FragmentPracticeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmentBinding = FragmentPracticeBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            fragmentPractice = this@PracticeFragment
        }
    }

    fun exit() {
        sharedViewModel.reset()
        findNavController().navigate(R.id.action_practiceFragment_to_mainFragment)

    }

    fun next() {
        sharedViewModel.incorrectAnswerTechnique() // TODO: Buscar si hay ota función más rápida
        checkLast()
    }

    fun previous() {
        if(!checkFirst())
            sharedViewModel.back()
        else
            findNavController().navigate(R.id.action_practiceFragment_to_sequenceMenuFragment2)

    }

    private fun checkLast() {
        // TODO: CAMBIAR POR MENSAJE PARA REVISAR SI QUIERE SALIR
        if (sharedViewModel.questionPosition.value == sharedViewModel.seqLength.value?.plus(1)) {
            sharedViewModel.back() // So counter doesn't go higher than sequence size
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Return to Main Menu?")
                .setNegativeButton("YES") { _, _ ->
                    findNavController().navigate(R.id.action_practiceFragment_to_mainFragment)
                    sharedViewModel.reset()
                }
                .setPositiveButton("NO") { _, _ ->
                }
                .show()
        }
    }

    private fun checkFirst():Boolean{
        if (sharedViewModel.questionPosition.value == 1){
            return true
        }
        return false
    }

}
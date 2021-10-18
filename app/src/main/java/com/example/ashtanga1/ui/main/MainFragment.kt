package com.example.ashtanga1.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.ashtanga1.R
import com.example.ashtanga1.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    //private val sharedViewModel: MainViewModel by activityViewModels()

    // Data binding with the XML
    private var binding: MainFragmentBinding? = null
    private val sharedViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate data binding
        val fragmentBinding = MainFragmentBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.mainFragment = this
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    fun goToNextScreen(mode: Int){
        when (mode) {
            0 -> {findNavController().navigate(R.id.action_mainFragment_to_sequenceMenuFragment2)
                sharedViewModel.setMode(0)}
            1 -> {findNavController().navigate(R.id.action_mainFragment_to_sequenceMenuFragment2)
                sharedViewModel.setMode(1)}
            2 -> {findNavController().navigate(R.id.action_mainFragment_to_sequenceMenuFragment2)
                sharedViewModel.setMode(2)}
            3 -> {findNavController().navigate(R.id.action_mainFragment_to_placeholder)
                // TODO: Ir a ventana de fichas
                sharedViewModel.setMode(3)}
        }
    }

}
package com.example.ashtanga1.ui.main

import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.ashtanga1.ReviewActivity
import com.example.ashtanga1.databinding.MainFragmentBinding

import android.util.TypedValue
import androidx.annotation.AttrRes
import com.example.ashtanga1.R
import com.example.ashtanga1.data.DataSource
import kotlin.random.Random


class MainFragment : Fragment() {

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

    fun goToNextScreen(mode: Int) {
        // TODO: Probar poner sharedViewModel.setMode(mode) afuera del when y agrupar los casos !3
        when (mode) {
            // Techniques
            0 -> {
                findNavController().navigate(com.example.ashtanga1.R.id.action_mainFragment_to_sequenceMenuFragment2)
                sharedViewModel.setMode(0)
            }
            // Sequence Order
            1 -> {
                findNavController().navigate(com.example.ashtanga1.R.id.action_mainFragment_to_sequenceMenuFragment2)
                sharedViewModel.setMode(1)
            }
            // Combined
            2 -> {
                combinedMode()
                findNavController().navigate(com.example.ashtanga1.R.id.action_mainFragment_to_sequenceMenuFragment2)
                sharedViewModel.setMode(2)
            }
            // Review
            3 -> {
                sharedViewModel.setMode(3)
                val intent = Intent(context, ReviewActivity::class.java)
                context?.startActivity(intent)
            }
            // Practice
            4 -> {
                sharedViewModel.setMode(4)
                findNavController().navigate(com.example.ashtanga1.R.id.action_mainFragment_to_sequenceMenuFragment2)
            }


        }
    }

    private fun combinedMode() {
        sharedViewModel.setRandomTech(true)
        sharedViewModel.setCombinedMode()
    }
}
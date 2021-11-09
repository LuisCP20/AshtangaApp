package com.example.ashtanga1.ui.main

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.ashtanga1.R
import com.example.ashtanga1.databinding.FragmentPostureQuestionBinding
import com.example.ashtanga1.model.Asana
import kotlin.random.Random

class PostureQuestionFragment : Fragment() {

    private val sharedViewModel: MainViewModel by activityViewModels()
    private var binding: FragmentPostureQuestionBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmentBinding = FragmentPostureQuestionBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.rootView?.setBackgroundResource(defBackg)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            fragmentPostureQuestion = this@PostureQuestionFragment
        }
    }

    fun checkAnswer(selection: Asana) {
        val view = view?.rootView
        val handle = Handler()
        sharedViewModel.enableButtons(false)
        Log.d("Test", "${selection}, ${sharedViewModel.asana.value}")
        // Comparar nombres deberia funcionar con Drishti y con imagenes
        if (selection.postureImageResourceId == (sharedViewModel.asana.value?.postureImageResourceId)) {
            view?.setBackgroundResource(rightColor)
            view?.postDelayed({ view.setBackgroundResource(defBackg) }, delayTime)
            handle.postDelayed({
                sharedViewModel.correctAnswerTechnique()
                checkLast()
                sharedViewModel.enableButtons(true)
            }, delayTime)
        } else {
            view?.setBackgroundResource(wrongColor)
            view?.postDelayed({ view.setBackgroundResource(defBackg) }, delayTime)
            handle.postDelayed({
                sharedViewModel.incorrectAnswerTechnique()
                checkLast()
                sharedViewModel.enableButtons(true)
            }, delayTime)
        }
    }

    // Check if current posture was the last one
    private fun checkLast() {
        if (sharedViewModel.questionPosition.value?.minus(1) ?: exit() == sharedViewModel.seqLength.value) {
            sharedViewModel.finalScoreVar = sharedViewModel.finalScoreString()
            findNavController().navigate(R.id.action_postureQuestionFragment2_to_finishedFragment2)
        } else {
            if (sharedViewModel.randomT.value == true) {
                val nextTech = randomTechnique()
                if (nextTech != 3) {
                    findNavController().navigate(R.id.action_postureQuestionFragment2_to_techniqueQuestionFragment)
                }
            }
        }
    }

    private fun randomTechnique(): Int {
        val techniques = listOf(0, 2, 3)
        val selected = techniques[Random.nextInt(0, techniques.size)]
        Log.d("RandomTech", "NextQ${selected}")
        sharedViewModel.setTechnique(selected)
        return selected
    }

    fun exit() {
        sharedViewModel.reset()
        findNavController().navigate(R.id.action_postureQuestionFragment2_to_mainFragment)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
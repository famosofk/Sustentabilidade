package com.example.sustentabilidade.fragment.certificationfragments.answerquestionfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.sustentabilidade.R
import com.example.sustentabilidade.databinding.FragmentAnswerQuestionBinding
import com.example.sustentabilidade.models.certificationmodels.Answer
import com.example.sustentabilidade.models.certificationmodels.Question

class AnswerQuestionFragment : Fragment() {

    private lateinit var binding: FragmentAnswerQuestionBinding
    private lateinit var viewModel: AnswerQuestionViewModel
    private lateinit var question: Question
    private var repeat: Boolean? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_answer_question, container, false)
        viewModel = ViewModelProvider(this).get(AnswerQuestionViewModel::class.java)
        viewModel.initializeAnswerList(arguments?.getString("certificationID")!!)
        question = viewModel.getQuestion(arguments?.getString("question")!!)
        setListeners()
        setObservers()

        return binding.root
    }


    private fun setListeners() {
        binding.switchNoteAnswer.setOnClickListener {
            if (binding.switchNoteAnswer.isChecked) {
                binding.noteLinearLayout.visibility = View.VISIBLE
            } else {
                binding.noteLinearLayout.visibility = View.GONE
            }
        }
        binding.repeatAnswerApplySystemButton.setOnClickListener {
            viewModel.saveAnswer(generateAnswer(), true)
        }
        binding.saveAnswerApplySystemButton.setOnClickListener {
            viewModel.saveAnswer(generateAnswer(), false)
        }
    }


    private fun generateAnswer(): Answer {
        val answer = Answer()
        answer.index = question.index
        answer.parentID = question.id
        when {
            binding.trueRadioButtonAnswerQuestion.isChecked -> answer.value = Question.ANSWER_POSSUI
            binding.falseRadioButtonAnswerQuestion.isChecked -> answer.value =
                Question.ANSWER_NAO_POSSUI
            binding.nonApplicableRadioButtonAnswerQuestion.isChecked -> answer.value =
                Question.ANSWER_NAO_SE_APLICA
        }
        if (binding.switchNoteAnswer.isChecked) {
            answer.note = binding.noteAnswerApplySystemEditText.text.toString()
            answer.deliveryDate = binding.dateAnswerApplySystemEditText.text.toString()
        }
        return answer
    }

    private fun setObservers() {
        viewModel.repeat.observe(viewLifecycleOwner, {

            if (it == true) {
                binding.root.findNavController()
                    .navigate(R.id.action_answerQuestionFragment_to_mainFragment)
            } else if (it == false) {
                binding.root.findNavController().navigate(
                    R.id.action_answerQuestionFragment_to_applyCertificationFragment,
                    generateBundle()
                )
            }

        })
    }

    private fun generateBundle(): Bundle {
        val bundle = Bundle()
        bundle.putString("certificationID", arguments?.getString("certificationID")!!)
        bundle.putString("farmCode", arguments?.getString("farmCode")!!)
        return bundle
    }





}
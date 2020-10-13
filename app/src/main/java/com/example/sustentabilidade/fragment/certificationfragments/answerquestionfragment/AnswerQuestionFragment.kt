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
import com.example.sustentabilidade.helpers.ScreenHelper
import com.example.sustentabilidade.models.certificationmodels.Answer
import com.example.sustentabilidade.models.certificationmodels.Question

class AnswerQuestionFragment : Fragment() {

    private lateinit var binding: FragmentAnswerQuestionBinding
    private lateinit var viewModel: AnswerQuestionViewModel

    //TODO we need to save answer


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_answer_question, container, false)
        viewModel = ViewModelProvider(this).get(AnswerQuestionViewModel::class.java)
        setListeners()
        setObservers()
        updateUI(viewModel.getQuestion(arguments?.getString("question")!!))

        return binding.root
    }

    private fun setObservers() {
        viewModel.allQuestionsAnswered.observe(viewLifecycleOwner, {
            if (it) {
                ScreenHelper.createToast(requireContext(), "Todas as perguntas foram respondidas")
                viewModel.allQuestionsAnswered.value = !(viewModel.allQuestionsAnswered.value)!!
            }
        })
    }

    private fun updateUI(question: Question) {
        viewModel.getAnswerList(
            arguments?.getString("farmCode")!!,
            arguments?.getString("certificationID")!!
        )
        binding.booleanOptions.visibility = View.VISIBLE
        ScreenHelper.createToast(requireContext(), "${question.index}")
        binding.questionNameApplySystemTextView.text = question.name
        binding.switchNoteAnswer.setOnClickListener {
            if (binding.switchNoteAnswer.isChecked) {
                binding.noteLinearLayout.visibility = View.VISIBLE
            } else {
                binding.noteLinearLayout.visibility = View.GONE
            }
        }
    }

    private fun setListeners() {
        binding.saveAnswerApplySystemEditText.setOnClickListener {
            val answer = generateAnswer()
            answer?.index = arguments?.getInt("index")!!
            answer?.let { ans -> viewModel.saveQuestion(ans) }
            binding.root.findNavController()
                .navigate(R.id.action_answerQuestionFragment_to_mainFragment)
        }
        binding.repeatAnswerApplySystemEditText.setOnClickListener {
            val answer = generateAnswer()
            answer?.index = arguments?.getInt("index")!!
            answer?.let { ans -> viewModel.saveQuestion(ans) }
            binding.root.findNavController().navigate(
                R.id.action_answerQuestionFragment_to_applyCertificationFragment,
                generateBundle()
            )

        }
    }

    private fun generateAnswer(): Answer? {
        val answer = Answer()
        answer.parentID = arguments?.getString("question")!!
        answer.farmID = arguments?.getString("farmCode")!!
        if (binding.switchNoteAnswer.isChecked) {
            if (binding.noteAnswerApplySystemEditText.text.toString().isNotEmpty() &&
                binding.dateAnswerApplySystemEditText.text.toString().isNotEmpty()
            ) {
                answer.note = binding.noteAnswerApplySystemEditText.text.toString()
                answer.deliveryDate = binding.dateAnswerApplySystemEditText.text.toString()
            } else {
                ScreenHelper.createToast(
                    requireContext(),
                    resources.getString(R.string.notes_intro)
                )
                return null
            }
        }
        return answer

    }

    private fun generateBundle(): Bundle {
        val bundle = Bundle()
        bundle.putString("certificationID", arguments?.getString("certificationID"))
        bundle.putString("farmCode", arguments?.getString("farmCode"))
        return bundle
    }


}
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

    //TODO we need to save answer


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_answer_question, container, false)
        viewModel = ViewModelProvider(this).get(AnswerQuestionViewModel::class.java)
        setListeners()
        updateUI(viewModel.getQuestion(arguments?.getString("question")!!))

        return binding.root
    }

    private fun updateUI(question: Question) {
        viewModel.getAnswerList(
            arguments?.getString("farmCode")!!,
            arguments?.getString("certificationID")!!
        )
        if (question.type == Question.BOOLEAN_INDICATOR_TYPE) {
            binding.booleanOptions.visibility = View.VISIBLE
        } else {
            binding.degreeValueApplySystemEditText.visibility = View.VISIBLE
            binding.textViewValueAnswer.visibility = View.VISIBLE
        }
        binding.questionNameApplySystemTextView.text = question.name
    }

    private fun setListeners() {
        binding.saveAnswerApplySystemEditText.setOnClickListener {
            generateAnswer()
            binding.root.findNavController()
                .navigate(R.id.action_answerQuestionFragment_to_mainFragment)
        }
        binding.repeatAnswerApplySystemEditText.setOnClickListener {
            generateAnswer()
            binding.root.findNavController().navigate(
                R.id.action_answerQuestionFragment_to_applyCertificationFragment,
                generateBundle()
            )

        }
    }

    private fun generateAnswer() {
        val answer = Answer()
        answer.parentID = arguments?.getString("question")!!
        answer.observacao = binding.noteAnswerApplySystemEditText.text.toString()
    }

    private fun generateBundle(): Bundle {
        val bundle = Bundle()
        bundle.putString("certificationID", arguments?.getString("certificationID"))
        bundle.putString("farmCode", arguments?.getString("farmCode"))
        return bundle
    }


}
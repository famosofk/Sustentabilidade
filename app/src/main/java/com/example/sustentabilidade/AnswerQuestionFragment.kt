package com.example.sustentabilidade

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.sustentabilidade.databinding.FragmentAnswerQuestionBinding
import com.example.sustentabilidade.models.certificationmodels.Question
import io.realm.Realm
import io.realm.kotlin.where

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AnswerQuestionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AnswerQuestionFragment : Fragment() {

    private lateinit var binding: FragmentAnswerQuestionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_answer_question, container, false)
        val id = arguments?.getString("question")!!
        val realm = Realm.getDefaultInstance()
        val question = realm.where<Question>().contains("id", id).findFirst()!!
        updateUI(question)

        return binding.root
    }

    private fun updateUI(question: Question) {
        if (question.type == Question.BOOLEAN_INDICATOR_TYPE) {
            binding.booleanOptions.visibility = View.VISIBLE
        } else {
            binding.degreeValueApplySystemEditText.visibility = View.VISIBLE
            binding.textViewValueAnswer.visibility = View.VISIBLE
        }
        binding.questionNameApplySystemTextView.text = question.name
    }


}
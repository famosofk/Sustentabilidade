package com.example.sustentabilidade

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.sustentabilidade.adapters.DirectQuestionAdapter
import com.example.sustentabilidade.models.certificationmodels.Certification
import io.realm.Realm
import io.realm.kotlin.where

class DirectAnswerFragment : Fragment() {

    private val adapter = DirectQuestionAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //todo refactor
        val v = inflater.inflate(R.layout.fragment_direct_question, container, false)
        val recyclerView = v.findViewById<RecyclerView>(R.id.recyclerViewDirectAnswer)
        recyclerView.adapter = adapter
        val realm = Realm.getDefaultInstance()
        val certification =
            realm.where<Certification>().contains("id", arguments?.getString("certificationID"))
                .findFirst()!!
        val title = v.findViewById<TextView>(R.id.directQuestionTitle)
        title.text = certification.name
        adapter.setCertificationIdFarmCode(
            arguments?.getString("certificationID")!!,
            arguments?.getString("farmCode")!!
        )
        adapter.submitList(certification.questionList)

        val button: Button = v.findViewById(R.id.saveDirectAnswerButton)
        button.setOnClickListener {
            if (adapter.answerList.answeredQuestions == certification.questionNameList.size) {
                adapter.answerList.finished = "1"
                adapter.answerList.sendAnswerListToCloud()
            }
            realm.beginTransaction()
            realm.copyToRealm(adapter.answerList)
            realm.commitTransaction()
            v.findNavController().navigate(R.id.action_directAnswerFragment_to_mainFragment)
        }

        return v
    }


}

package com.example.sustentabilidade

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.sustentabilidade.adapters.DirectQuestionAdapter
import com.example.sustentabilidade.helpers.ScreenHelper
import com.example.sustentabilidade.models.certificationmodels.Certification
import io.realm.Realm
import io.realm.kotlin.where

class DirectAnswerFragment : Fragment() {

    private val adapter = DirectQuestionAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_direct_question, container, false)
        val recycler: RecyclerView = v.findViewById(R.id.directAnswerRecyclerView)
        recycler.adapter = adapter
        val realm = Realm.getDefaultInstance()
        val certification =
            realm.where<Certification>().contains("id", arguments?.getString("certificationID"))
                .findFirst()!!
        ScreenHelper.createToast(requireContext(), "" + certification.toString())
        adapter.submitList(certification.questionList)

        return v
    }


}

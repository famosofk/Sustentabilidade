package com.example.sustentabilidade

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.sustentabilidade.adapters.VisualizeCertificationAdapter
import com.example.sustentabilidade.databinding.FragmentVisualizeCertificationBinding
import com.example.sustentabilidade.models.certificationmodels.AnswerList
import com.example.sustentabilidade.models.certificationmodels.Certification
import io.realm.Realm
import io.realm.kotlin.where

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class VisualizeCertificationFragment : Fragment() {

    private lateinit var binding: FragmentVisualizeCertificationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_visualize_certification,
            container,
            false
        )
        val realm = Realm.getDefaultInstance()
        val answerlist =
            realm.where<AnswerList>().contains("id", arguments?.getString("answerListId"))
                .findFirst()
        val certification =
            realm.where<Certification>().contains("id", answerlist!!.certificationID).findFirst()!!
        val adapter = VisualizeCertificationAdapter()
        adapter.attachCertification(certification)
        binding.answerRecyclerView.adapter = adapter
        adapter.submitList(answerlist.answerList)

        return binding.root
    }


}
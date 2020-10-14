package com.example.sustentabilidade.fragment.certificationfragments.applycertification

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sustentabilidade.R
import com.example.sustentabilidade.adapters.StringAdapter
import com.example.sustentabilidade.databinding.FragmentApplyCertificationBinding
import com.example.sustentabilidade.fragment.certificationfragments.managecertification.CreateCertificationItemClickListener
import com.example.sustentabilidade.models.certificationmodels.Certification
import io.realm.Realm
import io.realm.kotlin.where

class ApplyCertificationFragment : Fragment() {

    private lateinit var binding: FragmentApplyCertificationBinding
    private val adapter = StringAdapter()
    private var type = ""
    private val list = mutableListOf<String>()
    private lateinit var certification: Certification
    lateinit var array: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        array = resources.getStringArray(R.array.models)
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_apply_certification,
            container,
            false
        )

        recoveryData()
        return binding.root

    }

    private fun recoveryData() {
        initializeCertification(arguments?.getString("certificationID")!!)
        if (type == "") {
            type = array[0]
        }
        binding.toolbar2.title = "${certification.name}: $type"
        binding.toolbar2.setTitleTextColor(Color.WHITE)
        setUpAdapter()
    }

    private fun setUpAdapter() {
        adapter.attachListener(createRecyclerItemClickListener())
        binding.applyCertificationAdapter.layoutManager = LinearLayoutManager(context)
        binding.applyCertificationAdapter.itemAnimator = null
        binding.applyCertificationAdapter.adapter = adapter
        if (list.size == 0) {
            list.addAll(certification.dominionNameList)
        }
        adapter.submitList(list)

    }

    private fun submitListAdapter(p: Int) {
        list.clear()
        when (type) {
            array[0] -> {
                list.addAll(certification.themeNameList)
                type = array[1]
            }
            array[1] -> {
                list.addAll(certification.subThemeNameList)
                type = array[2]
            }
            array[2] -> {
                list.addAll(certification.questionNameList)
                type = array[3]
            }
        }
        adapter.submitList(null)
        adapter.submitList(list)
        binding.toolbar2.title = "${certification.name}: $type"


    }

    private fun createRecyclerItemClickListener(): CreateCertificationItemClickListener {
        return object : CreateCertificationItemClickListener {
            override fun onClick(p: Int) {
                if (type == array[3]) {
                    binding.root.findNavController().navigate(
                        R.id.action_applyCertificationFragment_to_answerQuestionFragment,
                        createBundle(p)
                    )
                } else {
                    submitListAdapter(p)
                }
            }
        }
    }

    private fun createBundle(p: Int): Bundle {
        val bundle = Bundle()
        bundle.putString("certificationID", certification.id)
        bundle.putString("question", certification.getQuestion(list[p])!!.id)
        bundle.putString("farmCode", arguments?.getString("farmCode")!!)
        return bundle
    }

    private fun initializeCertification(id: String) {
        val realm = Realm.getDefaultInstance()
        val results = realm.where<Certification>().contains("id", id).findFirst()!!
        certification = results
    }

}


























































































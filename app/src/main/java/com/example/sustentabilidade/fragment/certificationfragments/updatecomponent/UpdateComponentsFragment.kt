package com.example.sustentabilidade.fragment.certificationfragments.updatecomponent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.sustentabilidade.R
import com.example.sustentabilidade.databinding.FragmentUpdateComponentsBinding
import com.example.sustentabilidade.models.certificationmodels.Certification
import io.realm.Realm
import io.realm.kotlin.where


class UpdateComponentsFragment : Fragment() {


    private lateinit var binding: FragmentUpdateComponentsBinding
    private lateinit var certification: Certification
    private lateinit var array: Array<String>
    private lateinit var realm: Realm
    private lateinit var type: String
    private lateinit var name: String
    //TODO Refazer as operações de save and delete

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_update_components, container, false
        )
        realm = Realm.getDefaultInstance()
        configureUI()
        clickListeners()
        return binding.root
    }

    private fun clickListeners() {
        binding.deleteEditButton.setOnClickListener {
            realm.beginTransaction()
            when (type) {
                array[0] -> certification.removeItem(certification.getDominion(name)!!)

                array[1] -> certification.removeItem(certification.getTheme(name)!!)

                array[2] -> certification.removeItem(certification.getSubTheme(name)!!)

                array[3] -> certification.removeItem(certification.getQuestion(name)!!)

                array[4] -> certification.removeItem(certification.getQuestion(name)!!)
            }
            realm.commitTransaction()
        }
        binding.finishEditButton.setOnClickListener {
            realm.beginTransaction()
            when (type) {
                array[0] -> {
                    val dom = certification.getDominion(name)!!
                    dom.name = binding.updateComponentName.text.toString()
                    realm.copyToRealmOrUpdate(dom)
                }
                array[1] -> {
                    val theme = certification.getTheme(name)!!
                    theme.name = binding.updateComponentName.text.toString()
                    realm.copyToRealmOrUpdate(theme)
                }
                array[2] -> {
                    val subtheme = certification.getSubTheme(name)!!
                    subtheme.name = binding.updateComponentName.text.toString()
                    realm.copyToRealmOrUpdate(subtheme)
                }
                array[3] -> {
                    val question = certification.getQuestion(name)!!
                    question.name = binding.updateComponentName.text.toString()
                    realm.copyToRealmOrUpdate(question)
                }

            }
            realm.commitTransaction()
        }
    }


    private fun configureUI() {
        array = resources.getStringArray(R.array.models)
        type = arguments?.getString("type")!!
        val certificationID = arguments?.getString("certificationID")!!
        certification = realm.where<Certification>().contains("id", certificationID).findFirst()!!
        name = arguments?.getString("name")!!
        binding.updateComponentName.setText(name)
        if (type == array[3]) {
            binding.defineWeightSwitch.visibility = View.VISIBLE
            binding.weightIndicatorSignIn.visibility = View.VISIBLE
            realm.beginTransaction()
            val question = certification.getQuestion(name)!!
            realm.commitTransaction()
            if (question.weight != 1.toFloat()) {
                binding.defineWeightSwitch.isChecked = true
                binding.weightIndicatorSignIn.setText(question.weight.toString())
            }
        }

    }


}
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
import com.example.sustentabilidade.models.certificationmodels.Question
import io.realm.Realm
import io.realm.kotlin.where


class UpdateComponentsFragment : Fragment() {


    private lateinit var binding: FragmentUpdateComponentsBinding
    private lateinit var certification: Certification
    private lateinit var array: Array<String>
    private lateinit var realm: Realm
    private lateinit var type: String
    private lateinit var name: String

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
        return binding.root
    }

    private fun clickListeners() {
        binding.deleteEditButton.setOnClickListener { }
        binding.finishEditButton.setOnClickListener {
            realm.beginTransaction()
            when (type) {
                array[0] -> certification.getDominion(name)?.name =
                    binding.updateComponentName.text.toString()
                array[1] -> certification.getTheme(name)?.name =
                    binding.updateComponentName.text.toString()
                array[2] -> certification.getSubTheme(name)?.name =
                    binding.updateComponentName.text.toString()
                array[3] -> {
                    certification.getQuestion(name)?.name =
                        binding.updateComponentName.text.toString()
                }
            }
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
            binding.typeSelectorUpdateComponent.visibility = View.VISIBLE
            binding.indicatorTypeRadioGroup.visibility = View.VISIBLE
            binding.defineWeightSwitch.visibility = View.VISIBLE
            binding.weightIndicatorSignIn.visibility = View.VISIBLE
            realm.beginTransaction()
            val question = certification.getQuestion(name)!!
            realm.commitTransaction()
            if (question.type == Question.BOOLEAN_INDICATOR_TYPE) {
                binding.booleanTypeIndicator.isChecked = true
            } else {
                binding.valueTypeIndicator.isChecked = true
            }
            if (question.weight != 1.toFloat()) {
                binding.defineWeightSwitch.isChecked = true
                binding.weightIndicatorSignIn.setText(question.weight.toString())
            }
        }

    }


}
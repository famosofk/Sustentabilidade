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
    private lateinit var realm: Realm

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


    private fun configureUI() {
        val array = resources.getStringArray(R.array.models)
        val type = arguments?.getString("type")
        val certificationID = arguments?.getString("certificationID")!!
        certification = realm.where<Certification>().contains("id", certificationID).findFirst()!!
        val name = arguments?.getString("name")
        binding.updateComponentName.setText(name)
        if (type == array[3]) {

            binding.typeSelectorUpdateComponent.visibility = View.VISIBLE
            binding.indicatorTypeRadioGroup.visibility = View.VISIBLE
            binding.defineWeightSwitch.visibility = View.VISIBLE
            binding.weightIndicatorSignIn.visibility = View.VISIBLE
        }

    }


}
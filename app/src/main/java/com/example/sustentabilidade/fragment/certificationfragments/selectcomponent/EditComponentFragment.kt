package com.example.sustentabilidade.fragment.certificationfragments.selectcomponent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.sustentabilidade.R
import com.example.sustentabilidade.adapters.StringAdapter
import com.example.sustentabilidade.databinding.FragmentEditComponentBinding
import com.example.sustentabilidade.fragment.certificationfragments.managecertification.CreateCertificationItemClickListener
import com.example.sustentabilidade.models.certificationmodels.Certification
import io.realm.Realm
import io.realm.kotlin.where


class EditComponentFragment : Fragment() {

    private lateinit var binding: FragmentEditComponentBinding
    private lateinit var certification: Certification
    private lateinit var realm: Realm
    private val adapter = StringAdapter()
    private lateinit var componentsList: List<String>
    private lateinit var type: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_edit_component, container, false
        )
        verifyArguments()


        return binding.root
    }

    private fun verifyArguments() {
        if (arguments == null) {
            binding.root.findNavController()
                .navigate(R.id.action_editComponentFragment_to_certificationsFragment)
        } else {
            initializeVariables()
        }
    }

    private fun initializeVariables() {
        type = arguments?.getString("type")!!
        binding.toolbar.title = type
        realm = Realm.getDefaultInstance()
        certification =
            realm.where<Certification>().contains("id", arguments?.getString("certificationID")!!)
                .findFirst()!!
        val listener = createRecyclerItemClickListener()
        adapter.attachListener(listener)
        binding.recyclerManageItemList.adapter = adapter

        submitList(type)
    }

    private fun createRecyclerItemClickListener(): CreateCertificationItemClickListener {
        return object : CreateCertificationItemClickListener {
            override fun onClick(p: Int) {
                binding.root.findNavController().navigate(
                    R.id.updateComponentsFragment,
                    createBundle(p)
                )
            }
        }
    }

    private fun submitList(type: String) {
        val array = resources.getStringArray(R.array.models)
        when (type) {
            array[0] -> {
                componentsList = certification.getAllNames(Certification.DOMINION)
            }
            array[1] -> {
                componentsList = certification.getAllNames(Certification.THEME)
            }
            array[2] -> {
                componentsList = certification.getAllNames(Certification.SUB_THEME)
            }
            array[3] -> {
                componentsList = certification.getAllNames(Certification.QUESTION)
            }
        }
        adapter.submitList(componentsList)
    }

    private fun createBundle(p: Int): Bundle {
        val bundle = Bundle()
        bundle.putString("certificationID", certification.id)
        bundle.putString("type", type)
        bundle.putString("name", componentsList[p])
        return bundle

    }


}


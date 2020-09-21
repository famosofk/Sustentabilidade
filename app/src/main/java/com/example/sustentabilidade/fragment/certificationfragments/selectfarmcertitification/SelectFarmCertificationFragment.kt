package com.example.sustentabilidade.fragment.certificationfragments.selectfarmcertitification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.sustentabilidade.R
import com.example.sustentabilidade.adapters.StringAdapter
import com.example.sustentabilidade.databinding.FragmentSelectFarmCertificationBinding
import com.example.sustentabilidade.fragment.certificationfragments.managecertification.CreateCertificationItemClickListener
import com.example.sustentabilidade.helpers.ScreenHelper
import com.example.sustentabilidade.models.certificationmodels.Certification
import io.realm.Realm
import io.realm.kotlin.where

class SelectFarmCertificationFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentSelectFarmCertificationBinding
    private lateinit var viewModel: SelectFarmCertificationViewModel
    private lateinit var farmCode: String
    private var programName = ""
    private val adapter = StringAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_select_farm_certification,
            container,
            false
        )
        binding.farmAdapterTest.onItemSelectedListener = this
        viewModel = ViewModelProvider(this).get(SelectFarmCertificationViewModel::class.java)
        createAdapters()
        attachListToRecyclerView()
        return binding.root
    }

    private fun attachListToRecyclerView() {
        adapter.attachListener(createRecyclerItemClickListener())
        binding.selectSystemRecyclerView.adapter = adapter
        val realm = Realm.getDefaultInstance()
        val results = realm.where<Certification>().findAll()
        val list = mutableListOf<String>()
        if (results.size == 0) {
            ScreenHelper.createToast(requireContext(), "Crie um sistema primeiro.")
        }
        results.forEach {
            list.add(it.name)
        }
        adapter.submitList(list)
    }

    private fun createAdapters() {
        val programAdapter = context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_dropdown_item,
                viewModel.getFarmList()
            )
        }
        farmCode = viewModel.getFarmList()[0]
        binding.farmAdapterTest.adapter = programAdapter

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        farmCode = binding.farmAdapterTest.selectedItem.toString()
    }

    private fun createRecyclerItemClickListener(): CreateCertificationItemClickListener {
        return object : CreateCertificationItemClickListener {
            override fun onClick(p: Int) {
                if (farmCode != "") {
                    binding.root.findNavController()
                        .navigate(
                            R.id.action_selectFarmCertificationFragment_to_applyCertificationFragment,
                            createBundle(p)
                        )
                } else {
                    ScreenHelper.createToast(requireContext(), "Selecione uma fazenda.")
                }
            }
        }
    }

    private fun createBundle(p: Int): Bundle {
        val bundle = Bundle()
        val realm = Realm.getDefaultInstance()
        val results = realm.where<Certification>().findAll()
        results[p]?.id
        bundle.putString("program", programName)
        bundle.putString("farmCode", farmCode)
        bundle.putString("certificationID", results[p]?.id)
        return bundle
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}


}
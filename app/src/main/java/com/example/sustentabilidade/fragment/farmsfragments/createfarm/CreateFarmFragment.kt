package com.example.sustentabilidade.fragment.farmsfragments.createfarm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.sustentabilidade.R
import com.example.sustentabilidade.databinding.CreateFarmFragmentBinding
import com.example.sustentabilidade.models.gestaomodels.Program
import io.realm.Realm
import io.realm.kotlin.where

class CreateFarmFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var realm: Realm

    private lateinit var viewModel: CreateFarmViewModel
    private lateinit var binding: CreateFarmFragmentBinding
    private val list = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        realm = Realm.getDefaultInstance()
        viewModel = ViewModelProvider(this).get(CreateFarmViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.create_farm_fragment, container, false)

        initializeSpinners()
        initializeObservers()
        initializeListeners()


        return binding.root
    }

    private fun initializeListeners() {
        binding.createFarmButton.setOnClickListener {
            if (binding.farmNameEditText.text.toString()
                    .isNotEmpty() && binding.farmPasswordEditText.text.toString().isNotEmpty()
            ) {
                viewModel.createFarm(
                    binding.farmNameEditText.text.toString(),
                    binding.farmPasswordEditText.text.toString(),
                    realm
                )
            }
        }
    }

    private fun initializeObservers() {
        viewModel.mNavigation.observe(viewLifecycleOwner, {
            if (it) {
                Toast.makeText(context, "Fazenda criada com sucesso.", Toast.LENGTH_SHORT).show()
                binding.root.findNavController()
                    .navigate(R.id.action_createFarmFragment_to_mainFragment)
            }
        })
    }

    private fun initializeSpinners() {
        realm.beginTransaction()
        val results = realm.where<Program>().findAll()
        results.forEach {
            list.add(it.name)
        }
        viewModel.defineDefaultProgram(list[0])
        val adapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, list) }
        binding.spinner.onItemSelectedListener = this
        binding.spinner.adapter = adapter
        realm.commitTransaction()
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        viewModel.defineDefaultProgram(list[p2])

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

}
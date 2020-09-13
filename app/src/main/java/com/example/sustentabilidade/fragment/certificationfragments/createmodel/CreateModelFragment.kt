package com.example.sustentabilidade.fragment.certificationfragments.createmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sustentabilidade.R
import com.example.sustentabilidade.databinding.FragmentCreateModelBinding
import com.example.sustentabilidade.helpers.ScreenHelper

class CreateModelFragment : Fragment(), AdapterView.OnItemSelectedListener, View.OnClickListener {


    private lateinit var binding: FragmentCreateModelBinding
    private lateinit var viewModel: CreateModelViewModel
    private var parent = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(CreateModelViewModel::class.java)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_create_model, container, false)
        viewModel.getCertification(arguments?.getString("certificationID")!!)
        initializeList()
        setObservers()
        return binding.root
    }

    private fun setObservers() {
        viewModel.mFinish.observe(viewLifecycleOwner, {})
        viewModel.mRepeat.observe(viewLifecycleOwner, {})
    }

    private fun initializeList() {
        if (arguments?.getString("type")!! == "Domínio") {
            binding.parentModelSpinner.visibility = View.GONE
            binding.associateTextView.visibility = View.GONE
        } else {
            viewModel.initializeArray(resources.getStringArray(R.array.models))
            viewModel.initializeList(arguments?.getString("type")!!)
            val adapter = context?.let {
                ArrayAdapter(
                    it, android.R.layout.simple_spinner_dropdown_item,
                    viewModel.list
                )
            }
            binding.parentModelSpinner.adapter = adapter
            binding.parentModelSpinner.onItemSelectedListener = this
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        parent = viewModel.list[p2]
        ScreenHelper.createToast(requireContext(), parent)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }


}
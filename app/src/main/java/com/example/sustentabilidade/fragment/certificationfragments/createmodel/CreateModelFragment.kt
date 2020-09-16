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
import androidx.navigation.findNavController
import com.example.sustentabilidade.R
import com.example.sustentabilidade.databinding.FragmentCreateModelBinding
import com.example.sustentabilidade.helpers.ScreenHelper
import com.example.sustentabilidade.models.certificationmodels.Question

class CreateModelFragment : Fragment(), AdapterView.OnItemSelectedListener, View.OnClickListener {


    private lateinit var binding: FragmentCreateModelBinding
    private lateinit var viewModel: CreateModelViewModel
    private var parent = ""
    private var indicatorType = 0

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
        viewModel.mFinish.observe(viewLifecycleOwner, {
            if (it) {
                binding.root.findNavController()
                    .navigate(R.id.action_createModelFragment_to_certificationsFragment)
            }
        })
        viewModel.mRepeat.observe(viewLifecycleOwner, {
            if (it) {
                binding.inputNameSignIn.text.clear()
                viewModel.turnRepetBackToFalse()
            }
        })
    }

    private fun initializeList() {
        binding.certificationTitle.text = arguments?.getString("type")
        viewModel.initializeArray(resources.getStringArray(R.array.models))
        if (arguments?.getString("type")!! == "Dimensão") {
            binding.parentModelSpinner.visibility = View.GONE
            binding.associateTextView.visibility = View.GONE
        } else {

            viewModel.initializeList(arguments?.getString("type")!!)
            val adapter = context?.let {
                ArrayAdapter(
                    it, android.R.layout.simple_spinner_dropdown_item,
                    viewModel.list
                )
            }
            binding.parentModelSpinner.adapter = adapter
            binding.parentModelSpinner.onItemSelectedListener = this

            if (arguments?.getString("type")!! == "Indicador") {
                binding.indicatorTypeTextView.visibility = View.VISIBLE
                binding.defineWeightSwitch.visibility = View.VISIBLE
                binding.indicatorTypeRadioGroup.visibility = View.VISIBLE
                binding.defineWeightSwitch.setOnClickListener(this)
            }
        }
        binding.finishSignInButton.setOnClickListener(this)
        binding.signInAgainButton.setOnClickListener(this)

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        parent = viewModel.list[p2]
        ScreenHelper.createToast(requireContext(), parent)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.defineWeightSwitch) {

            if (binding.defineWeightSwitch.isChecked) {
                binding.weightIndicatorSignIn.visibility = View.VISIBLE
            } else {
                binding.weightIndicatorSignIn.visibility = View.GONE
            }

        } else {

            var command = 0
            if (p0?.id == R.id.finishSignInButton) {
                command = 1
            }
            if (binding.booleanTypeIndicator.isChecked) {
                indicatorType = Question.BOOLEAN_INDICATOR_TYPE
            } else {
                indicatorType = Question.VALUE_INDICATOR_TYPE
            }

            viewModel.createModel(
                arguments?.getString("type")!!,
                binding.inputNameSignIn.text.toString(),
                parent,
                command = command,
                binding.weightIndicatorSignIn.text.toString(),
                indicatorType
            )
        }
    }


}